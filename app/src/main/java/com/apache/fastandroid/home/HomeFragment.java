package com.apache.fastandroid.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.apache.fastandroid.jetpack.StateData;
import com.apache.fastandroid.network.model.Article;
import com.apache.fastandroid.network.model.HomeArticleResponse;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tesla.framework.ui.fragment.ARecycleViewSwipeRefreshFragmentNew;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.Observer;

/**
 * Created by Jerry on 2021/7/1.
 */
public class HomeFragment extends ARecycleViewSwipeRefreshFragmentNew {


    public static HomeFragment newInstance(){
        return new HomeFragment();
    }


    private HomeViewModel homeViewModel;
    @Override
    protected void initViewModel() {
        homeViewModel = getFragmentScopeViewModel(HomeViewModel.class);
    }

    @Override
    protected BaseQuickAdapter createAdapter() {
        return new ArticleAdapter(null);
    }

    private int mTopArticlesLoadTimes;

    @Override
    public void layoutInit(LayoutInflater inflater, Bundle savedInstanceState) {
        super.layoutInit(inflater, savedInstanceState);


        homeViewModel.getHomeArticleLiveData().observe(this, new Observer<StateData<HomeArticleResponse>>() {
            @Override
            public void onChanged(StateData<HomeArticleResponse> listData) {
                //置顶文章
                homeViewModel.getTopArticleLiveData().observe(HomeFragment.this, new Observer<StateData<List<Article>>>() {
                    @Override
                    public void onChanged(StateData<List<Article>> topData) {
                        //置顶文章只添加一次
                        if (isFristPage() && mTopArticlesLoadTimes == 0){
                            mTopArticlesLoadTimes++;
                            if (topData.isSuccess() && listData.isSuccess()){
                                List<Article> topList = topData.getData();
                                List<Article> middleList = listData.getData().getDatas();
                                List<Article> totalList = new ArrayList<>();
                                totalList.addAll(topList);
                                totalList.addAll(middleList);
                                handleData(totalList,true);
                            }else if (listData.isSuccess()){
                                handleData(listData.getData().getDatas(),true);
                            }else {
                                showLoadErrorView("", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        onRefresh();
                                    }
                                });
                            }
                            dismissRefreshing();
                        }
                    }
                });

                //非第一页
                if (!isFristPage()){
                    if (listData.isSuccess()){
                        handleData(listData.getData().getDatas(),false);
                    }else {
                        getAdapter().loadMoreFail();
                    }
                }

            }
        });

        onRefresh();
    }


    private void handleData(List<Article> data, boolean isRefresh){
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            getAdapter().setNewData(data);

        } else {
            getAdapter().addData(data);
        }
        dismissRefreshing();
        // 返回列表为空显示加载完毕
        if (size == 0 ) {
            //只有大于一页，才展示"no more data"
            getAdapter().loadMoreEnd(isFristPage());
        } else {
            getAdapter().loadMoreComplete();
        }
    }

    private boolean isFristPage(){
        return mCurrentPage == 0;
    }


    private int mCurrentPage = 0;

    @Override
    public void onRefresh() {
        showRefreshing();
        mCurrentPage = 0;
        mTopArticlesLoadTimes = 0;
        homeViewModel.loadHomeData(mCurrentPage);
    }

    @Override
    protected void onLoadMore() {
        homeViewModel.loadHomeData(++mCurrentPage);
    }

}

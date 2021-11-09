package com.apache.fastandroid.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.apache.fastandroid.article.ArticleDetailActivity;
import com.apache.fastandroid.article.ArticleViewModel;
import com.apache.fastandroid.bean.CollectBean;
import com.apache.fastandroid.jetpack.StateData;
import com.apache.fastandroid.network.model.Article;
import com.apache.fastandroid.network.model.HomeArticleResponse;
import com.apache.fastandroid.state.UserInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tesla.framework.ui.fragment.ARecycleViewSwipeRefreshFragmentNew;
import com.wjx.android.wanandroidmvvm.common.state.callback.CollectListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.Observer;

/**
 * Created by Jerry on 2021/7/1.
 */
public class HomeFragment extends ARecycleViewSwipeRefreshFragmentNew implements CollectListener {
    public static final String TAG ="HomeFragment";


    public static HomeFragment newInstance(){
        return new HomeFragment();
    }


    private HomeViewModel homeViewModel;

    private ArticleViewModel mArticleViewModel;

    @Override
    protected void initViewModel() {
        homeViewModel = getFragmentScopeViewModel(HomeViewModel.class);
        mArticleViewModel = getFragmentScopeViewModel(ArticleViewModel.class);
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
//                                ToastUtils.showShort("list data:"+ listData);
//                                List<Article> middleList = listData.getData().getDatas();
                                List<Article> totalList = new ArrayList<>();
                                totalList.addAll(topList);
//                                totalList.addAll(middleList);
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
        mArticleViewModel.stateLiveData.observe(this, new Observer<StateData<CollectBean>>() {
            @Override
            public void onChanged(StateData<CollectBean> stateData) {
                if (stateData.isSuccess()){
                    CollectBean item = stateData.getData();
                    getItemById(item.getId()).setCollect(item.getStatus());
                    getAdapter().notifyDataSetChanged();
                }
            }

        });

        onRefresh();
        getAdapter().setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Article item = (Article) getAdapter().getItem(position);
                ArticleDetailActivity.launch(getActivity(),item.getTitle(),item.getLink());
            }
        });
        getAdapter().setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Article item = (Article) getAdapter().getItem(position);
                UserInfo.getInstance().collect(getActivity(), item.getId(),HomeFragment.this);
            }
        });
    }


    private Article getItemById(int id){
        List<Article> datas= getAdapter().getData();
        for (Article article : datas) {
            if (id == article.getId()){
                return article;
            }
        }
        return null;
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

    @Override
    public void collect(@NotNull int id) {
        if (getItemById(id).getCollect()){
            mArticleViewModel.unCollect(id);
        }else {
            mArticleViewModel.collect(id);
        }

    }
}

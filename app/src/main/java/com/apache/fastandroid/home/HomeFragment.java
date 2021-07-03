package com.apache.fastandroid.home;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.apache.fastandroid.bean.Article;
import com.apache.fastandroid.bean.HomeArticleResponse;
import com.apache.fastandroid.jetpack.StateData;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tesla.framework.ui.fragment.ARecycleViewSwipeRefreshFragmentNew;

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


    @Override
    public void layoutInit(LayoutInflater inflater, Bundle savedInstanceState) {
        super.layoutInit(inflater, savedInstanceState);

        homeViewModel.getTopArticleLiveData().observe(this, new Observer<StateData<List<Article>>>() {
            @Override
            public void onChanged(StateData<List<Article>> listStateData) {
                if (listStateData.isSuccess()){
                    if (mCurrentPage == 0){
                        handleData(listStateData.getData(),getSwipeRefreshLayout().isRefreshing());
                    }

                }
            }
        });
        homeViewModel.getHomeArticleLiveData().observe(this, new Observer<StateData<HomeArticleResponse>>() {
            @Override
            public void onChanged(StateData<HomeArticleResponse> homeArticleResponseStateData) {
                if (homeArticleResponseStateData.isSuccess()){
                    handleData(homeArticleResponseStateData.getData().getDatas(),getSwipeRefreshLayout().isRefreshing());
                }
            }
        });

        onRefresh();
    }


    private void handleData(List<Article> list, boolean isRefreshing){
        if (isRefreshing){
            getSwipeRefreshLayout().setRefreshing(false);
        }
        if (list.isEmpty()){
            //no more data
            getAdapter().loadMoreEnd();
            return;
        }
        if (isRefreshing){
            getAdapter().setNewData(list);
            getAdapter().loadMoreComplete();
            return;
        }
        getAdapter().addData(list);
        getAdapter().loadMoreComplete();
    }



    private int mCurrentPage = 0;

    @Override
    public void onRefresh() {
        getSwipeRefreshLayout().setRefreshing(true);
        mCurrentPage = 0;
        homeViewModel.loadHomeData(mCurrentPage);
    }

    @Override
    protected void onLoadMore() {
        homeViewModel.loadHomeData(++mCurrentPage);
    }

}

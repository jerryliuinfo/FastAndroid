package com.apache.fastandroid.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.apache.fastandroid.R;
import com.apache.fastandroid.bean.Article;
import com.apache.fastandroid.bean.HomeArticleResponse;
import com.apache.fastandroid.jetpack.StateData;
import com.blankj.utilcode.util.ktx.ColorUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tesla.framework.databinding.ComUiFragmentRecycleviewSwiperefreshlayoutBinding;
import com.tesla.framework.ui.fragment.BaseLifecycleFragment;

import java.util.List;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Created by Jerry on 2021/7/1.
 */
public class HomeFragment extends BaseLifecycleFragment<ComUiFragmentRecycleviewSwiperefreshlayoutBinding> {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    public static HomeFragment newInstance(){
        return new HomeFragment();
    }

    private BaseQuickAdapter<Article, BaseViewHolder> adapter;


    private HomeViewModel homeViewModel;
    @Override
    protected void initModel() {
        homeViewModel = getFragmentScopeViewModel(HomeViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.com_ui_fragment_recycleview_swiperefreshlayout;
    }

    @Override
    public void bindUI(View rootView) {


        swipeRefreshLayout = viewDataBinding.swipeRefreshLayout;
        recyclerView = viewDataBinding.recycleview;
        initRefreshLayout();
        initAdapter();
        swipeRefreshLayout.setRefreshing(true);
        homeViewModel.loadHomeData(mCurrentPage);

    }

    private void initAdapter(){
        adapter = new ArticleAdapter(null);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.setEnableLoadMore(true);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                onLoadMore();
            }
        },recyclerView);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        homeViewModel.getTopArticleLiveData().observe(this, new Observer<StateData<List<Article>>>() {
            @Override
            public void onChanged(StateData<List<Article>> listStateData) {
                viewDataBinding.swipeRefreshLayout.setRefreshing(false);
                if (listStateData.isSuccess()){
                    if (mCurrentPage == 0){
                        handleData(listStateData.getData());
                    }

                }
            }
        });
        homeViewModel.getHomeArticleLiveData().observe(this, new Observer<StateData<HomeArticleResponse>>() {
            @Override
            public void onChanged(StateData<HomeArticleResponse> homeArticleResponseStateData) {
                if (homeArticleResponseStateData.isSuccess()){
                    handleData(homeArticleResponseStateData.getData().getDatas());
                }
            }
        });
    }

    private void handleData(List<Article> list){
        if (list.isEmpty()){
            adapter.loadMoreEnd();
            hideSwipreRefresh();
            return;
        }
        if (swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
            adapter.setNewData(list);
            adapter.loadMoreComplete();
            return;
        }
        adapter.addData(list);
        adapter.loadMoreComplete();
    }

    private void hideSwipreRefresh(){
        if (swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
    }


    private int mCurrentPage = 0;
    private void initRefreshLayout(){
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(ColorUtil.getColor(getContext()));
        swipeRefreshLayout.setColorSchemeColors(Color.WHITE);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doOnRefresh();
            }
        });
    }

    private void onLoadMore(){
        homeViewModel.loadHomeData(++mCurrentPage);
    }

    private void doOnRefresh(){
        mCurrentPage = 0;
        homeViewModel.loadHomeData(mCurrentPage);

    }
}

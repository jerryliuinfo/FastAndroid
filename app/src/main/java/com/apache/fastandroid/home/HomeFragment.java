package com.apache.fastandroid.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.apache.fastandroid.article.ArticleDetailActivity;
import com.apache.fastandroid.article.ArticleViewModel;
import com.apache.fastandroid.bean.CollectBean;
import com.apache.fastandroid.network.model.Article;
import com.apache.fastandroid.network.model.HomeArticleResponse;
import com.apache.fastandroid.state.UserInfo;
import com.apache.fastandroid.util.InjectorUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tesla.framework.databinding.CommUiRecycleviewSwiperefreshNewBinding;
import com.tesla.framework.ui.fragment.ARecycleViewSwipeRefreshFragmentNew;
import com.wjx.android.wanandroidmvvm.common.state.callback.CollectListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

/**
 * Created by Jerry on 2021/7/1.
 */
public class HomeFragment extends ARecycleViewSwipeRefreshFragmentNew implements CollectListener {
    public static final String TAG ="HomeFragment";


    public static HomeFragment newInstance(){
        return new HomeFragment();
    }


//    private HomeViewModel homeViewModel;

    private HomeViewModelKt mHomeViewModelKt;

    private ArticleViewModel mArticleViewModel;



    @Override
    protected void initViewModel() {
//        homeViewModel = getFragmentScopeViewModel(HomeViewModel.class);

        mHomeViewModelKt = new ViewModelProvider(this, InjectorUtil.getHomeModelFactory()).get(HomeViewModelKt.class);
        mArticleViewModel = new ViewModelProvider(this, InjectorUtil.getArticeModelFactory()).get(ArticleViewModel.class);
    }

    @Override
    protected BaseQuickAdapter createAdapter() {
        return new ArticleAdapter(null);
    }

    private int mTopArticlesLoadTimes;

    @Override
    public void layoutInit(LayoutInflater inflater, Bundle savedInstanceState) {
        super.layoutInit(inflater, savedInstanceState);

        mHomeViewModelKt.getHomeArticleLiveData().observe(this, new Observer<HomeArticleResponse>() {
            @Override
            public void onChanged(HomeArticleResponse listData) {
                mHomeViewModelKt.getTopArticleLiveData().observe(HomeFragment.this, new Observer<List<Article>>() {
                    @Override
                    public void onChanged(List<Article> topList) {
                        if (isFristPage() && mTopArticlesLoadTimes == 0){
                            mTopArticlesLoadTimes++;
                            if (topList != null && listData != null){
                                List<Article> totalList = new ArrayList<>();
                                totalList.addAll(topList);
                                totalList.addAll(listData.getDatas());
                                handleData(totalList,true);
                            }else if (listData != null){
                                handleData(listData.getDatas(),true);
                            }else {

                            }
                            dismissRefreshing();
                        }
                    }
                });
                //非第一页
                if (!isFristPage()){
                    if (listData != null){
                        handleData(listData.getDatas(),false);
                    }else {
                        getAdapter().loadMoreFail();
                    }
                }

            }
        });

        mArticleViewModel.getStatus().observe(this, new Observer<CollectBean>() {
            @Override
            public void onChanged(CollectBean item) {
                getItemById(item.getId()).setCollect(item.getStatus());
                getAdapter().notifyDataSetChanged();
            }
        });


        onRefresh();
        getAdapter().setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Article item = (Article) getAdapter().getItem(position);
//                ArticleDetailActivity.launch(getActivity(),item.getTitle(),item.getLink());
                ArticleDetailActivity.launch(getActivity(),item.getTitle(),"https://ykt.eduyun.cn");
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
        mHomeViewModelKt.loadHomeData(mCurrentPage);

    }

    @Override
    protected CommUiRecycleviewSwiperefreshNewBinding bindView() {
        return null;
    }

    @Override
    protected void onLoadMore() {
        mHomeViewModelKt.loadHomeData(++mCurrentPage);
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


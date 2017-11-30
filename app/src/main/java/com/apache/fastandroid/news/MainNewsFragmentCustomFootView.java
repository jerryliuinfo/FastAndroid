package com.apache.fastandroid.news;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apache.fastandroid.news.view.MainNewsFootView;
import com.apache.fastandroid.news.view.MainNewsItemViewCreator;
import com.apache.fastandroid.support.bean.NewsChannelTable;
import com.apache.fastandroid.support.bean.NewsSummary;
import com.apache.fastandroid.support.bean.NewsSummaryBeans;
import com.apache.fastandroid.support.sdk.Sdk;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.support.paging.IPaging;
import com.tesla.framework.support.paging.index.IndexPaging;
import com.tesla.framework.ui.activity.FragmentArgs;
import com.tesla.framework.ui.activity.FragmentContainerActivity;
import com.tesla.framework.ui.fragment.ARecycleViewSwipeRefreshFragment;
import com.tesla.framework.ui.fragment.itemview.IITemView;
import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;

import java.util.List;

/**
 * Created by 01370340 on 2017/11/23.
 */

public class MainNewsFragmentCustomFootView extends ARecycleViewSwipeRefreshFragment<NewsSummary,NewsSummaryBeans,NewsSummary> {

    public static void launch(Activity from,NewsChannelTable mChannel) {
        FragmentArgs args =  new FragmentArgs();
        args.add("channel", mChannel);
        FragmentContainerActivity.launch(from,MainNewsFragmentCustomFootView.class,args);
    }

    private NewsChannelTable mChannel;
    public static MainNewsFragmentCustomFootView newFragment(NewsChannelTable channel) {
        Bundle args = new Bundle();
        args.putSerializable("channel",channel);
        MainNewsFragmentCustomFootView fragment = new MainNewsFragmentCustomFootView();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public IItemViewCreator<NewsSummary> configItemViewCreator() {
        return new MainNewsItemViewCreator(getActivity());
    }

    @Override
    protected IItemViewCreator<NewsSummary> configFooterItemViewCreator() {
        return new IItemViewCreator<NewsSummary>() {
            @Override
            public View newContentView(LayoutInflater inflater, ViewGroup parent, int viewType) {
                return inflater.inflate(MainNewsFootView.LAY_RES, parent, false);
            }

            @Override
            public IITemView<NewsSummary> newItemView(View contentView, int viewType) {
                return new MainNewsFootView(getActivity(), contentView, MainNewsFragmentCustomFootView.this);
            }
        };
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        if (savedInstanceSate == null){
            mChannel = (NewsChannelTable) getArguments().getSerializable("channel");
        }else {
            mChannel = (NewsChannelTable) savedInstanceSate.getSerializable("channel");
        }
    }

    @Override
    public void requestData(RefreshMode mode) {
        super.requestData(mode);
        if (mode == RefreshMode.refresh){
            mode = RefreshMode.reset;
        }
        new LoadNewsTask(mode).execute();
    }


    @Override
    protected IPaging<NewsSummary, NewsSummaryBeans> newPaging() {
        return new IndexPaging<>();
    }

    class LoadNewsTask extends APagingTask<Void,Void,NewsSummaryBeans>{

        public LoadNewsTask(RefreshMode mode) {
            super(mode);
        }

        @Override
        protected List<NewsSummary> parseResult(NewsSummaryBeans newsSummaryBeans) {
            return newsSummaryBeans.list;
        }

        @Override
        protected NewsSummaryBeans workInBackground(RefreshMode mode, String previousPage, String nextPage, Void... params) throws TaskException {
            int page = 0;
            //上拉
            if (TextUtils.isEmpty(nextPage)){
                page = Integer.parseInt(nextPage);
            }

            return Sdk.newInstance(getTaskCacheMode(this)).getNewsListData(mChannel.getNewsChannelType(),mChannel.getNewsChannelId(),String.valueOf(page));
        }
    }



}

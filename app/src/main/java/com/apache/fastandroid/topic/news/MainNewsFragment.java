package com.apache.fastandroid.topic.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import com.apache.fastandroid.topic.news.view.MainNewsItemViewCreator;
import com.apache.fastandroid.topic.support.bean.NewsChannelTable;
import com.apache.fastandroid.topic.support.bean.NewsSummary;
import com.apache.fastandroid.topic.support.bean.NewsSummaryBeans;
import com.apache.fastandroid.topic.support.sdk.Sdk;
import com.tesla.framework.common.util.N;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.support.paging.IPaging;
import com.tesla.framework.support.paging.index.IndexPaging;
import com.tesla.framework.ui.fragment.ARecycleViewSwipeRefreshFragment;
import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;
import java.util.List;

/**
 * Created by 01370340 on 2017/11/23.
 */

public class MainNewsFragment extends ARecycleViewSwipeRefreshFragment<NewsSummary,NewsSummaryBeans,NewsSummary> {

    private NewsChannelTable mChannel;
    public static MainNewsFragment newFragment(NewsChannelTable channel) {
        Bundle args = new Bundle();
        args.putSerializable("channel",channel);
        MainNewsFragment fragment = new MainNewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public IItemViewCreator<NewsSummary> configItemViewCreator() {
        return new MainNewsItemViewCreator(getActivity());
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
            if (!N.isEmpty(nextPage)){
                page = Integer.parseInt(nextPage);
            }

            return Sdk.newInstance(getTaskCacheMode(this)).getNewsListData(mChannel.getNewsChannelType(),mChannel.getNewsChannelId(),String.valueOf(page));
        }
    }



}

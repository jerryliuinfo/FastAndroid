package com.apache.fastandroid.topic;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.apache.fastandroid.topic.bean.TopicBean;
import com.apache.fastandroid.topic.bean.TopicBeans;
import com.apache.fastandroid.topic.sdk.TopicSDK;
import com.apache.fastandroid.topic.view.TopicItemView;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.support.paging.IPaging;
import com.tesla.framework.support.paging.index.IndexPaging;
import com.tesla.framework.ui.activity.FragmentArgs;
import com.tesla.framework.ui.activity.FragmentContainerActivity;
import com.tesla.framework.ui.fragment.ARecycleViewSwipeRefreshFragment;
import com.tesla.framework.ui.fragment.itemview.BaseItemViewCreator;
import com.tesla.framework.ui.fragment.itemview.IITemView;
import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;

import java.util.List;

/**
 * Created by 01370340 on 2017/9/2.
 */

public class TopicListFragment extends ARecycleViewSwipeRefreshFragment<TopicBean,TopicBeans,TopicBean> {

    @Override
    public IItemViewCreator<TopicBean> configItemViewCreator() {
        return new BaseItemViewCreator<TopicBean>(getActivity()) {
            @Override
            protected int inflateItemView(int viewType) {
                return TopicItemView.LAYOUT_RES;
            }

            @Override
            public IITemView<TopicBean> newItemView(View contentView, int viewType) {
                return new TopicItemView(getContext(),contentView);
            }
        };
    }

    class LoadTopicTask extends APagingTask<Void,Void,TopicBeans>{

        public LoadTopicTask(RefreshMode mode) {
            super(mode);
        }

        @Override
        protected List<TopicBean> parseResult(TopicBeans topicBeans) {
            return topicBeans.list;
        }

        @Override
        protected TopicBeans workInBackground(RefreshMode mode, String previousPage, String nextPage, Void... params) throws TaskException {
            int offset = 0;
            if (!TextUtils.isEmpty(nextPage)){
                offset = Integer.parseInt(nextPage);
            }
            return TopicSDK.newInstance().getTopicsList(null,null,offset,20);

        }
    }










    @Override
    public void requestData(RefreshMode mode) {
        super.requestData(mode);
        if (mode == RefreshMode.refresh){
            mode = RefreshMode.reset;
        }
        new LoadTopicTask(mode).execute();
    }

    @Override
    protected IPaging<TopicBean, TopicBeans> newPaging() {
        return new IndexPaging<>();
    }







    public static void launch(Activity from) {
        FragmentArgs args =  new FragmentArgs();
        FragmentContainerActivity.launch(from,TopicListFragment.class,args);
    }
    public static TopicListFragment newFragment() {
        Bundle args = new Bundle();
        TopicListFragment fragment = new TopicListFragment();
        fragment.setArguments(args);
        return fragment;
    }
}

package com.apache.fastandroid.topic;

import android.os.Bundle;
import android.text.TextUtils;

import com.apache.fastandroid.topic.bean.TopicBean;
import com.apache.fastandroid.topic.bean.TopicBeans;
import com.apache.fastandroid.topic.sdk.TopicSDK;
import com.apache.fastandroid.topic.view.TopicItemViewCreator;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.support.paging.IPaging;
import com.tesla.framework.support.paging.index.IndexPaging;
import com.tesla.framework.ui.fragment.ARecycleViewSwipeRefreshFragment;
import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;

import java.util.List;

/**
 * Created by 01370340 on 2017/9/2.
 */

public class TopicFragment extends ARecycleViewSwipeRefreshFragment<TopicBean,TopicBeans,TopicBean> {

    public static TopicFragment newFragment() {
        Bundle args = new Bundle();
        TopicFragment fragment = new TopicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IItemViewCreator<TopicBean> configItemViewCreator() {
        return new TopicItemViewCreator(getActivity());
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
            try {
                TopicBeans beans =  TopicSDK.newInstance().getTopicsList(null,null,offset,20);
                NLog.d(TAG, "workInBackground beans = %s", beans);
                return beans;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }
    }
}

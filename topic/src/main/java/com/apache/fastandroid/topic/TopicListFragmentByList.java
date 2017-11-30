package com.apache.fastandroid.topic;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.apache.fastandroid.topic.bean.TopicBean;
import com.apache.fastandroid.topic.bean.TopicBeans;
import com.apache.fastandroid.topic.sdk.TopicSDK;
import com.apache.fastandroid.topic.view.TopicItemViewCreator;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.support.paging.IPaging;
import com.tesla.framework.support.paging.index.IndexPaging;
import com.tesla.framework.ui.activity.FragmentArgs;
import com.tesla.framework.ui.activity.FragmentContainerActivity;
import com.tesla.framework.ui.fragment.AListSwipeRefreshFragment;
import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;

import java.util.List;

/**
 * Created by 01370340 on 2017/9/2.
 */

public class TopicListFragmentByList extends AListSwipeRefreshFragment<TopicBean,TopicBeans,TopicBean> {

    public static void launch(Activity from) {
        FragmentArgs args =  new FragmentArgs();
        FragmentContainerActivity.launch(from,TopicListFragmentByList.class,args);
    }
    public static TopicListFragmentByList newFragment() {
        Bundle args = new Bundle();
        TopicListFragmentByList fragment = new TopicListFragmentByList();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public IItemViewCreator<TopicBean> configItemViewCreator() {
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
            return TopicSDK.newInstance().getTopicsList(null,null,offset,20);

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        TopicDetailFragment.start(getActivity(),getAdapterItems().get(position));
    }
}

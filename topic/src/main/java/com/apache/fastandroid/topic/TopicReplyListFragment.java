package com.apache.fastandroid.topic;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.apache.fastandroid.topic.bean.TopicBean;
import com.apache.fastandroid.topic.bean.TopicReplyBean;
import com.apache.fastandroid.topic.bean.TopicReplyBeans;
import com.apache.fastandroid.topic.sdk.TopicSDK;
import com.apache.fastandroid.topic.view.TopicIDetailReplyViewCreator;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.network.task.WorkTask;
import com.tesla.framework.ui.fragment.APagingFragment;
import com.tesla.framework.ui.fragment.ARecycleViewFragment;
import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;

import java.util.ArrayList;

/**
 * Created by 01370340 on 2017/9/16.
 */

public class TopicReplyListFragment extends ARecycleViewFragment<TopicReplyBean,TopicReplyBeans,TopicReplyBean>{

    private TopicBean mTopicBean;
    public static TopicReplyListFragment newInstance(TopicBean topicBean){
        TopicReplyListFragment fragment = new TopicReplyListFragment();
        Bundle args = new Bundle();
        args.putSerializable("topic",topicBean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IItemViewCreator<TopicReplyBean> configItemViewCreator() {
        return new TopicIDetailReplyViewCreator(getActivity());
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        if (savedInstanceSate == null){
            mTopicBean = (TopicBean) getArguments().getSerializable("topic");
        }else {
            mTopicBean = (TopicBean) savedInstanceSate.getSerializable("topic");
        }
    }

    @Override
    public void requestData() {
        super.requestData();
        loadReplyList();
    }

    private void loadReplyList(){
        new WorkTask<Void,Void,ArrayList<TopicReplyBean>>(){

            @Override
            public ArrayList<TopicReplyBean> workInBackground(Void... params) throws TaskException {
                ArrayList<TopicReplyBean> replyList = null;
                try {
                     replyList = TopicSDK.newInstance().getReplyList(mTopicBean.id,null,mTopicBean.replies_count);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return replyList;
            }

            @Override
            protected void onSuccess(ArrayList<TopicReplyBean> topicReplyBeen) {
                super.onSuccess(topicReplyBeen);
                NLog.d(APagingFragment.TAG, "topicReplyBeen = %s", topicReplyBeen);
                setItems(topicReplyBeen);
            }
        }.execute();
    }
}

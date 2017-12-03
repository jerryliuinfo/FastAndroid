package com.apache.fastandroid.topic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.apache.fastandroid.topic.bean.TopicBean;
import com.apache.fastandroid.topic.bean.TopicReplyBean;
import com.apache.fastandroid.topic.bean.TopicReplyBeans;
import com.apache.fastandroid.topic.sdk.TopicSDK;
import com.apache.fastandroid.topic.view.TopicItemReplyView;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.network.task.WorkTask;
import com.tesla.framework.ui.fragment.APagingFragment;
import com.tesla.framework.ui.fragment.ARecycleViewFragment;
import com.tesla.framework.ui.fragment.itemview.BaseItemViewCreator;
import com.tesla.framework.ui.fragment.itemview.IITemView;
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
    public IItemViewCreator<TopicReplyBean> configItemViewCreator() {
        return new BaseItemViewCreator<TopicReplyBean>(getActivity()) {
            @Override
            protected int inflateItemView(int viewType) {
                return TopicItemReplyView.LAYOUT_RES;
            }

            @Override
            public IITemView<TopicReplyBean> newItemView(View contentView, int viewType) {
                return new TopicItemReplyView(getActivity(),contentView);
            }
        };
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

                return TopicSDK.newInstance().getReplyList(mTopicBean.id,null,mTopicBean.replies_count);
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

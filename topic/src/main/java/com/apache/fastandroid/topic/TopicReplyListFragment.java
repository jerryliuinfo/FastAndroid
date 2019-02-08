package com.apache.fastandroid.topic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.apache.fastandroid.topic.bean.TopicBean;
import com.apache.fastandroid.topic.bean.TopicReplyBean;
import com.apache.fastandroid.topic.bean.TopicReplyBeans;
import com.apache.fastandroid.topic.sdk.TopicSDK;
import com.apache.fastandroid.topic.view.TopicItemReplyView;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.support.bean.RefreshConfig;
import com.tesla.framework.ui.fragment.ARecycleViewFragment;
import com.tesla.framework.ui.fragment.itemview.BaseItemViewCreator;
import com.tesla.framework.ui.fragment.itemview.IITemView;
import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;

import java.util.List;

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
    public int inflateContentView() {
        return R.layout.fragment_topic_reply_list;
    }

    @Override
    protected void setUpRefreshConfig(RefreshConfig refreshConfig) {
        super.setUpRefreshConfig(refreshConfig);
        refreshConfig.disalbeFooterMore();

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

    private void updateReplyCountText(){
        TextView tv_reply_count = (TextView) findViewById(R.id.reply_count);
        tv_reply_count.setText("共收到 " + mTopicBean.replies_count + "条回复");
        tv_reply_count.setVisibility(View.VISIBLE);
    }

    private void hideReplyCountText(){
        TextView tv_reply_count = (TextView) findViewById(R.id.reply_count);
        tv_reply_count.setVisibility(View.INVISIBLE);
    }



    @Override
    public void requestData(RefreshMode mode) {
        super.requestData(mode);
        new LoadReplyTask(mode).execute();
    }




    class LoadReplyTask extends APagingTask<Void,Void,TopicReplyBeans>{

        public LoadReplyTask(RefreshMode mode) {
            super(mode);
        }

        @Override
        protected List<TopicReplyBean> parseResult(TopicReplyBeans topicReplyBeans) {
            return topicReplyBeans.list;
        }

        @Override
        protected TopicReplyBeans workInBackground(RefreshMode mode, String previousPage, String nextPage, Void...
                params) throws TaskException {
            TopicReplyBeans beans =  TopicSDK.newInstance().getReplyList(mTopicBean.id,null,mTopicBean.replies_count);

            return beans;
        }

        @Override
        protected void onSuccess(TopicReplyBeans topicReplyBeans) {
            super.onSuccess(topicReplyBeans);
            updateReplyCountText();
        }

        @Override
        protected void onFailure(TaskException exception) {
            super.onFailure(exception);
            hideReplyCountText();
        }
    }
}

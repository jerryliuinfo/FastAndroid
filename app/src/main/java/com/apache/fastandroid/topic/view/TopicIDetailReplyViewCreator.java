package com.apache.fastandroid.topic.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apache.fastandroid.topic.bean.TopicReplyBean;
import com.tesla.framework.ui.fragment.itemview.IITemView;
import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;

/**
 * Created by 01370340 on 2017/9/3.
 */

public class TopicIDetailReplyViewCreator implements IItemViewCreator<TopicReplyBean> {
    Activity context;
    public TopicIDetailReplyViewCreator(Activity context){
        this.context = context;
    }

    @Override
    public View newContentView(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return inflater.inflate(TopicItemReplyView.LAYOUT_RES,parent,false);
    }

    @Override
    public IITemView<TopicReplyBean> newItemView(View contentView, int viewType) {
        return new TopicItemReplyView(context,contentView);
    }


}
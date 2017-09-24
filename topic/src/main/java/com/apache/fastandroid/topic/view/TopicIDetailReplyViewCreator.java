package com.apache.fastandroid.topic.view;

import android.app.Activity;
import android.view.View;

import com.apache.fastandroid.topic.bean.TopicReplyBean;
import com.tesla.framework.ui.fragment.itemview.BaseItemViewCreator;
import com.tesla.framework.ui.fragment.itemview.IITemView;

/**
 * Created by 01370340 on 2017/9/3.
 */

public class TopicIDetailReplyViewCreator extends BaseItemViewCreator<TopicReplyBean> {


    public TopicIDetailReplyViewCreator(Activity context) {
        super(context);
    }

    @Override
    public IITemView<TopicReplyBean> newItemView(View contentView, int viewType) {
        return new TopicItemReplyView(getContext(),contentView);
    }

    @Override
    protected int inflateItemView() {
        return TopicItemReplyView.LAYOUT_RES;
    }


}
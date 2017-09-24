package com.apache.fastandroid.topic.view;

import android.app.Activity;
import android.view.View;

import com.apache.fastandroid.topic.bean.TopicBean;
import com.tesla.framework.ui.fragment.itemview.BaseItemViewCreator;
import com.tesla.framework.ui.fragment.itemview.IITemView;

/**
 * Created by 01370340 on 2017/9/3.
 */

public class TopicItemViewCreator extends BaseItemViewCreator<TopicBean> {


    public TopicItemViewCreator(Activity context) {
        super(context);
    }

    @Override
    protected int inflateItemView() {
        return TopicItemView.LAYOUT_RES;
    }

    @Override
    public IITemView<TopicBean> newItemView(View contentView, int viewType) {
        return new TopicItemView(getContext(),contentView);
    }


}
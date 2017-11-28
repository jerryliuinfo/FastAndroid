package com.apache.fastandroid.test;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.support.bean.HeaderBean;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.fragment.itemview.ARecycleViewItemViewHolder;

/**
 * Created by jerryliu on 2017/4/4.
 */

public class HeadItem2ViewHolder extends ARecycleViewItemViewHolder<HeaderBean> {
    @ViewInject(idStr = "tv_header")
    TextView headView;
    public HeadItem2ViewHolder(Activity context, View itemView) {
        super(context, itemView);
    }

    @Override
    public void onBindView(View convertView) {
        super.onBindView(convertView);
        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NLog.d(TestRecycleHeaderViewFragment.TAG, "onclick header view");
            }
        });
    }

    @Override
    public void onBindData(View convertView, HeaderBean data, int position) {

    }







}

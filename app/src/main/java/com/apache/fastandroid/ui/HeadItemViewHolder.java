package com.apache.fastandroid.ui;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.apache.fastandroid.ui.fragment.TestRecycleViewFragment;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.support.bean.HeaderBean;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.fragment.itemview.ARecycleViewItemViewHolder;

/**
 * Created by jerryliu on 2017/4/4.
 */

public class HeadItemViewHolder extends ARecycleViewItemViewHolder<HeaderBean> {
    @ViewInject(idStr = "tv_header")
    TextView headView;
    public HeadItemViewHolder(Activity context, View itemView) {
        super(context, itemView);
    }

    @Override
    public void onBindView(View convertView) {
        super.onBindView(convertView);
        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NLog.d(TestRecycleViewFragment.TAG, "onclick header view");
            }
        });
    }

    @Override
    public void onBindData(View convertView, HeaderBean data, int position) {

    }



    public void updateHeaderView(HeaderBean bean){
        headView.setText(bean.title);
    }



}

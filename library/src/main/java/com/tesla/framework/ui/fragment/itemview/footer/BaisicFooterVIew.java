package com.tesla.framework.ui.fragment.itemview.footer;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.tesla.framework.R;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.fragment.ABaseFragment;
import com.tesla.framework.ui.fragment.APagingFragment;

import java.io.Serializable;

/**
 * Created by jerryliu on 2017/4/3.
 */

public class BaisicFooterVIew<T extends Serializable> extends AFooterItemViewHolder<T> {

    public static final int LAY_RES = R.layout.comm_lay_footerview;

    protected View mFooterView;

    @ViewInject(idStr = "btnMore")
    TextView btnMore;
    @ViewInject(idStr = "layLoading")
    View layLoading;
    @ViewInject(idStr = "txtLoading")
    TextView txtLoading;

    public BaisicFooterVIew(Activity context, View itemView, OnFooterViewCallback callback) {
        super(context, itemView, callback);
        mFooterView = itemView;

        btnMore = (TextView) itemView.findViewById(R.id.btnMore);
        layLoading =  itemView.findViewById(R.id.layLoading);
        txtLoading = (TextView) itemView.findViewById(R.id.txtLoading);



        btnMore.setVisibility(View.VISIBLE);
        layLoading.setVisibility(View.GONE);

        btnMore.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (getCallback() != null && getCallback().canLoadMore()) {
                    getCallback().onLoadMore();
                }
            }

        });
    }

    @Override
    public void onBindView(View convertView) {
        super.onBindView(convertView);
    }

    @Override
    public void onBindData(View convertView, T data, int position) {

    }


    @Override
    public View getConvertView() {
        return mFooterView;
    }

    @Override
    public void onTaskStateChanged(AFooterItemViewHolder<?> footerItemView, ABaseFragment.ABaseTaskState state, TaskException exception, APagingFragment.RefreshMode mode) {
        if (state == ABaseFragment.ABaseTaskState.prepare) {
            if (mode == APagingFragment.RefreshMode.update) {
                txtLoading.setText(loadingText());
                layLoading.setVisibility(View.VISIBLE);
                btnMore.setVisibility(View.GONE);
                btnMore.setText(moreText());
            }
        }
        else if (state == ABaseFragment.ABaseTaskState.success) {
            if ((mode == APagingFragment.RefreshMode.update || mode == APagingFragment.RefreshMode.reset)) {
                if (!getCallback().canLoadMore()) {
                    btnMore.setText(endpagingText());
                } else {
                    btnMore.setText(moreText());
                }
            }
        }
        else if (state == ABaseFragment.ABaseTaskState.falid) {
            if (mode == APagingFragment.RefreshMode.update) {
                if (layLoading.getVisibility() == View.VISIBLE) {
                    btnMore.setText(faildText());
                }
            }
        }else if (state == ABaseFragment.ABaseTaskState.finished) {
            if (mode == APagingFragment.RefreshMode.update) {
                if (layLoading.getVisibility() == View.VISIBLE) {
                    layLoading.setVisibility(View.GONE);
                    btnMore.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void setFooterViewToRefreshing() {
        if (layLoading.getVisibility() != View.VISIBLE){
            layLoading.setVisibility(View.VISIBLE);
            if (getCallback() != null && getCallback().canLoadMore()){
                getCallback().onLoadMore();
            }
        }
    }


    protected String moreText() {
        return getContext().getString(R.string.comm_footer_more);
    }

    protected String loadingText() {
        return getContext().getString(R.string.comm_footer_loading);
    }

    protected String endpagingText() {
        return getContext().getString(R.string.comm_footer_pagingend);
    }

    protected String faildText() {
        return getContext().getString(R.string.comm_footer_faild);
    }
}

package com.tesla.framework.ui.fragment.itemview.footer;

import android.app.Activity;
import android.view.View;

import com.tesla.framework.ui.fragment.itemview.ARecycleViewItemViewHolder;

import java.io.Serializable;

/**
 * Created by jerryliu on 2017/4/3.
 */

public abstract class AFooterItemViewHolder<T extends Serializable> extends ARecycleViewItemViewHolder<T> implements OnFootViewListener{

    OnFooterViewCallback mOnFooterViewCallback;

    public AFooterItemViewHolder(Activity context, View itemView, OnFooterViewCallback callback) {
        super(context, itemView);
        this.mOnFooterViewCallback = callback;
    }

    public OnFooterViewCallback getCallback() {
        return mOnFooterViewCallback;
    }

    public interface OnFooterViewCallback{
        boolean canLoadMore();
        void onLoadMore();
    }

}

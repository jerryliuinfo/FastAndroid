package com.tesla.framework.ui.fragment.itemview;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tesla.framework.support.inject.InjectUtility;

import java.io.Serializable;

/**
 * Created by jerryliu on 2017/4/3.
 */

public abstract class ARecycleViewItemViewHolder<T extends Serializable> extends RecyclerView.ViewHolder implements IITemView<T>  {

    private Activity mContext;

    private View itemView;

    private int size;

    private int position;


    public ARecycleViewItemViewHolder(Activity context,View itemView) {
        super(itemView);
        this.mContext = context;
        this.itemView = itemView;
    }

    public Activity getContext() {
        return mContext;
    }

    @Override
    public View getConvertView() {
        return itemView;
    }

    @Override
    public int itemPosition() {
        return position;
    }


    @Override
    public int itemSize() {
        return size;
    }

    @Override
    public void onBindView(View convertView) {
        InjectUtility.initInjectedView(getContext(), this, convertView);
    }

    @Override
    public void reset(int size, int position) {
        this.size = size;
        this.position = position;
    }



}

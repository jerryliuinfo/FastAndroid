package com.tesla.framework.ui.fragment.itemview;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;

/**
 * Created by 01370340 on 2017/9/23.
 */

public abstract class BaseItemViewCreator<T extends Serializable> implements IItemViewCreator<T> {
    Activity context;

    public BaseItemViewCreator(Activity context) {
        this.context = context;
    }

    @Override
    public View newContentView(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return inflater.inflate(inflateItemView(),parent,false);
    }


    protected abstract int inflateItemView();

    public Activity getContext() {
        return context;
    }
}

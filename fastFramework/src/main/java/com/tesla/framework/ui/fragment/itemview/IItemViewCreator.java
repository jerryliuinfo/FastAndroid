package com.tesla.framework.ui.fragment.itemview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;

/**
 * Created by jerryliu on 2017/4/3.
 */

public interface IItemViewCreator<T extends Serializable> {

    View newContentView(LayoutInflater inflater, ViewGroup parent, int viewType);

    IITemView<T> newItemView(View contentView, int viewType);

}

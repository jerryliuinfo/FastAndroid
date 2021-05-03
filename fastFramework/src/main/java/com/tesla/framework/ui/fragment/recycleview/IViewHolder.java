package com.tesla.framework.ui.fragment.recycleview;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

/**
 * Created by Jerry on 2021/5/2.
 */
public interface IViewHolder<T,VH> {
    VH onCreateHolder(@NonNull ViewGroup parent, int viewType);

    void onBindHolder(@NonNull VH holder, int position);
}

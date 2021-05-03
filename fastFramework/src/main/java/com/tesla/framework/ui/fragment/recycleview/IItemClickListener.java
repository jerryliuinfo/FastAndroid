package com.tesla.framework.ui.fragment.recycleview;

/**
 * Created by Jerry on 2021/5/2.
 */
public interface IItemClickListener<T> {
    void onClick(T data, int position);
}

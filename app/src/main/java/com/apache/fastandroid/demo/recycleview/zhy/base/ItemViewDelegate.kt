package com.apache.fastandroid.demo.recycleview.zhy.base

/**
 * Created by zhy on 16/6/22.
 */
interface ItemViewDelegate<T> {
    val itemViewLayoutId: Int
    fun isForViewType(item: T, position: Int): Boolean
    fun convert(holder: ViewHolder, t: T, position: Int)
}
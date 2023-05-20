package com.apache.fastandroid.demo.recycleview.zhy.base

import androidx.collection.SparseArrayCompat

/**
 * Created by zhy on 16/6/22.
 */
class ItemViewDelegateManager<T> {
    //Key: Int 代表 ViewType , Value:ItemViewDelegate
    var delegates: SparseArrayCompat<ItemViewDelegate<T>> = SparseArrayCompat<ItemViewDelegate<T>>()
    val itemViewDelegateCount: Int
        get() = delegates.size()

    fun addDelegate(delegate: ItemViewDelegate<T>?): ItemViewDelegateManager<T> {
        val viewType = delegates.size()
        if (delegate != null) {
            delegates.put(viewType, delegate)
        }
        return this
    }

    fun addDelegate(viewType: Int, delegate: ItemViewDelegate<T>?): ItemViewDelegateManager<T> {
        require(delegates[viewType] == null) {
            ("An ItemViewDelegate is already registered for the viewType = "
                    + viewType
                    + ". Already registered ItemViewDelegate is "
                    + delegates[viewType])
        }
        delegates.put(viewType, delegate)
        return this
    }

    fun removeDelegate(delegate: ItemViewDelegate<T>?): ItemViewDelegateManager<T> {
        if (delegate == null) {
            throw NullPointerException("ItemViewDelegate is null")
        }
        val indexToRemove = delegates.indexOfValue(delegate)
        if (indexToRemove >= 0) {
            delegates.removeAt(indexToRemove)
        }
        return this
    }

    fun removeDelegate(itemType: Int): ItemViewDelegateManager<T> {
        val indexToRemove = delegates.indexOfKey(itemType)
        if (indexToRemove >= 0) {
            delegates.removeAt(indexToRemove)
        }
        return this
    }

    fun getItemViewType(item: T, position: Int): Int {
        val delegatesCount = delegates.size()
        for (i in delegatesCount - 1 downTo 0) {
            val delegate = delegates.valueAt(i)
            if (delegate!!.isForViewType(item, position)) {
                return delegates.keyAt(i)
            }
        }
        throw IllegalArgumentException(
            "No ItemViewDelegate added that matches position=$position in data source"
        )
    }

    fun convert(holder: ViewHolder?, item: T, position: Int) {
        val delegatesCount = delegates.size()
        for (i in 0 until delegatesCount) {
            val delegate = delegates.valueAt(i)
            if (delegate!!.isForViewType(item, position)) {
                delegate.convert(holder!!, item, position)
                return
            }
        }
        throw IllegalArgumentException(
            "No ItemViewDelegateManager added that matches position=$position in data source"
        )
    }

    fun getItemViewDelegate(viewType: Int): ItemViewDelegate<*>? {
        return delegates[viewType]
    }

    fun getItemViewLayoutId(viewType: Int): Int {
        return getItemViewDelegate(viewType)!!.itemViewLayoutId
    }

    fun getItemViewType(itemViewDelegate: ItemViewDelegate<T>?): Int {
        return delegates.indexOfValue(itemViewDelegate)
    }
}
package com.apache.fastandroid.demo.recycleview.callback

import androidx.recyclerview.widget.DiffUtil
import com.apache.fastandroid.demo.recycleview.bean.DiffItemBean

/**
 * Created by Jerry on 2023/7/1.
 */
class DiffCallback(private val oldList: List<DiffItemBean>, private val newList: List<DiffItemBean>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }

}

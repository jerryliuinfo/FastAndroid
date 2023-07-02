package com.apache.fastandroid.demo.recycleview.callback

import androidx.recyclerview.widget.DiffUtil
import com.apache.fastandroid.demo.recycleview.bean.DiffItemBean

/**
 * Created by Jerry on 2023/7/2.
 */
object DiffItemCallback: DiffUtil.ItemCallback<DiffItemBean>() {
    override fun areItemsTheSame(oldItem: DiffItemBean, newItem: DiffItemBean): Boolean {
        println("AsyncListDifferAdapter areItemsTheSame oldItem id:${oldItem.id}, newItem id:${newItem.id}, thread:${Thread.currentThread().name}")
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DiffItemBean, newItem: DiffItemBean): Boolean {
        return oldItem == newItem
    }
}
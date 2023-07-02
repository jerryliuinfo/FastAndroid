package com.apache.fastandroid.demo.recycleview.listadapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.apache.fastandroid.demo.recycleview.diffcallback.DiffItemBean
import com.apache.fastandroid.demo.recycleview.callback.DiffItemCallback
import com.apache.fastandroid.demo.recycleview.viewholder.DiffItemViewHolder

/**
 * Created by Jerry on 2023/7/2.
 */
class MyListAdapter :ListAdapter<DiffItemBean,DiffItemViewHolder>(DiffItemCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiffItemViewHolder {
        return DiffItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DiffItemViewHolder, position: Int) {
        holder.bind(position, getItem(position))
    }


}




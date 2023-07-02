package com.apache.fastandroid.demo.recycleview.diffcallback

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.demo.recycleview.callback.DiffItemCallback
import com.apache.fastandroid.demo.recycleview.viewholder.DiffItemViewHolder

/**
 * Created by Jerry on 2023/7/1.
 */
class AsyncListDifferAdapter: RecyclerView.Adapter<DiffItemViewHolder>() {

    /**
     * AsyncListDiffer 可以处理在后台线程上进行列表差异计算的任务，从而避免阻塞 UI 线程。它通过异步方式计算并提供有关旧列表和新列表之间的差异信息，然后根据这些差异信息更新列表的显示。
     */
    private val differ = AsyncListDiffer(this, DiffItemCallback)

    fun submitList(newList: List<DiffItemBean>) {
        differ.submitList(newList)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiffItemViewHolder {
        return DiffItemViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    fun getItem(position: Int):DiffItemBean{
        return differ.currentList[position]
    }

    override fun onBindViewHolder(holder: DiffItemViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(position,item)
    }
}
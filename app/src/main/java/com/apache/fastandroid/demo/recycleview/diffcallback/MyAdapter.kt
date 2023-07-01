package com.apache.fastandroid.demo.recycleview.diffcallback

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.databinding.LayoutItemType1Binding
import com.apache.fastandroid.databinding.LayoutRecycleviewItemDiffCallbackBinding
import com.tesla.framework.component.logger.Logger

/**
 * Created by Jerry on 2023/7/1.
 */
class MyAdapter(var datas:List<DiffItemBean>): RecyclerView.Adapter<MyAdapter.ItemViewHolder>() {



    inner class ItemViewHolder(binding:LayoutRecycleviewItemDiffCallbackBinding):RecyclerView.ViewHolder(binding.root){
        val tvId = binding.tvId
        val tvName = binding.tvName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = LayoutRecycleviewItemDiffCallbackBinding.inflate(LayoutInflater.from(parent.context))
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = datas[position]
        Logger.d("onBindViewHolder position:$position, item:$item")
        holder.tvId.text = item.id.toString()
        holder.tvName.text = item.name
    }
}
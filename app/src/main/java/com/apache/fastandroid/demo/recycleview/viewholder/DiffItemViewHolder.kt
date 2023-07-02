package com.apache.fastandroid.demo.recycleview.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.databinding.LayoutRecycleviewItemBinding
import com.apache.fastandroid.demo.recycleview.bean.DiffItemBean
import com.tesla.framework.component.logger.Logger

/**
 * Created by Jerry on 2023/7/2.
 */


class DiffItemViewHolder(binding: LayoutRecycleviewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val tvId = binding.tvId
    private val tvName = binding.tvName

    companion object {
        fun from(parent: ViewGroup): DiffItemViewHolder {
            val binding = LayoutRecycleviewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return DiffItemViewHolder(binding)
        }
    }


    fun bind(position: Int, item: DiffItemBean) {
        Logger.d("onBindViewHolder position:$position, item:$item")
        tvId.text = item.id.toString()
        tvName.text = item.name
    }
}
package com.apache.fastandroid.demo.widget.mergeadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.ItemBinding
import masteryi.me.mergeadapterdemo.BaseAdapter
import masteryi.me.mergeadapterdemo.BaseViewHolder

/**
 * @author Ethan Lee
 */
class ItemAdapter(context: Context) : BaseAdapter(context) {
    inner class ItemViewHolder(itemView: View) : BaseViewHolder(itemView) {
        var binding: ItemBinding = DataBindingUtil.bind(itemView)!!

        override fun onBindView(position: Int) {
            binding.data = position.toString()
            binding.executePendingBindings()
        }
    }

    val itemData = arrayListOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        return ItemViewHolder(view)
    }



    override fun getItemCount(): Int {
        return itemData.size
    }

    fun addItem(data: List<Int>) {
        val start = itemData.size
        itemData.addAll(data)
        notifyItemRangeInserted(start, data.size)
    }
}
package com.apache.fastandroid.demo.sample.listentry

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.apache.fastandroid.demo.bean.MainItem
import com.apache.fastandroid.view.MainItemView
import com.tesla.framework.kt.getString
import com.tesla.framework.kt.layoutInflater

typealias OnMainItemClickListener = (MainItem) -> Unit

class MainAdapter(
    private val data: List<MainItem>
) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    var onMainItemClickListener: OnMainItemClickListener? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): MainViewHolder {
        return MainViewHolder(
                viewGroup
                        .layoutInflater
                        .inflate(
                                R.layout.view_main_item_inflatable,
                                viewGroup,
                                false
                        ) as MainItemView
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(viewHolder: MainViewHolder, position: Int) {
        val mainItem = data[position]
        val mainItemView = viewHolder.mainItemView
        mainItemView.setImageResource(
                if (position % 2 == 0) R.drawable.blue_circle else R.drawable.green_circle
        )
        mainItemView.title = mainItemView.getString(mainItem.title)
        mainItemView.description = mainItemView.getString(mainItem.description)
        mainItemView.setOnClickListener { onMainItemClickListener?.invoke(mainItem) }
    }

    class MainViewHolder(
        mainItemView: MainItemView
    ) : RecyclerView.ViewHolder(mainItemView) {
        val mainItemView: MainItemView get() = itemView as MainItemView
    }
}

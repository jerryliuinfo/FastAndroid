package com.apache.fastandroid.demo.sample.listentry

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.apache.fastandroid.demo.bean.MainItem
import com.apache.fastandroid.view.MainItemView
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.kt.getString
import com.tesla.framework.ui.fragment.adpater.DefaultViewHolder

typealias OnMainItemClickListener = (MainItem) -> Unit

class MainAdapter(
    private val data: List<MainItem>
) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    var onMainItemClickListener: OnMainItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): MainViewHolder {

        return MainViewHolder(
            MainItemView(parent.context)
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(viewHolder: MainViewHolder, position: Int) {
        viewHolder.bind(data[position])
    }


    private val itemCallback = ItemCallback()


    private inner class ItemCallback : MainItemView.Callback<MainItem?> {
        override fun onClick(item: MainItem?) {
            Logger.d("onClick item:$item")
        }

        override fun onLongClick(item: MainItem?): Boolean {
            Logger.d("onLongClick item:$item")

            return true
        }

    }

    override fun onViewAttachedToWindow(holder: MainViewHolder) {
        super.onViewAttachedToWindow(holder)

        holder.view.callback = itemCallback
    }

    override fun onViewDetachedFromWindow(holder: MainViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.view.callback = null

    }

    class MainViewHolder(
        itemView: MainItemView<MainItem>
    ) : DefaultViewHolder<MainItemView<MainItem>>(itemView) {

        fun bind(mainItem: MainItem) {
            view.apply {
                item = mainItem
                setTitle(mainItem.title.getString())
                setDesc(mainItem.description.getString())
                setImage(if (position % 2 == 0) R.drawable.blue_circle else R.drawable.green_circle)
            }

        }
    }
}

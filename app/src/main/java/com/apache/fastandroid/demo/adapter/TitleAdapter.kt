package com.apache.fastandroid.demo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.ItemCommentBinding
import com.tesla.framework.kt.showShortToast
import kotlinx.android.synthetic.main.item_comment.view.*

/**
 * Created by Jerry on 2022/6/26.
 */
class TitleAdapter(private val items:List<String>):RecyclerView.Adapter<TitleAdapter.TitleViewHolder>() {

    inner class TitleViewHolder(val itemView:View): RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        init {
            itemView.setOnClickListener {
                showShortToast("TitleAdapter onClick position:${bindingAdapterPosition}")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context))
        return TitleViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: TitleViewHolder, position: Int) {
        holder.tvTitle.text = items[position]
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
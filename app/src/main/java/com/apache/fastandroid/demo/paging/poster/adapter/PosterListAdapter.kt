package com.apache.fastandroid.demo.paging.poster.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.databinding.PosterListItemBinding
import com.apache.fastandroid.network.model.ArticleApi

class PosterListAdapter : PagingDataAdapter<ArticleApi, PosterViewHolder>(DataDifferntiator) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterViewHolder {
        val binding = PosterListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PosterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PosterViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }


    private object DataDifferntiator : DiffUtil.ItemCallback<ArticleApi>() {

        override fun areItemsTheSame(oldItem: ArticleApi, newItem: ArticleApi): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ArticleApi, newItem: ArticleApi): Boolean {
            return oldItem == newItem
        }
    }

}

package com.apache.fastandroid.demo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.databinding.ItemCommentBinding

/**
 * Created by Jerry on 2023/11/26.
 */
 class UserAdapter(val users:List<String>): RecyclerView.Adapter<UserAdapter.ItemViewHolder>() {


    inner class ItemViewHolder(binding: ItemCommentBinding): RecyclerView.ViewHolder(binding.root) {
        val tvTitle = binding.tvTitle
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context))
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = users[position]
        holder.tvTitle.text = data

    }

    override fun getItemCount(): Int {
        return users.size
    }
}
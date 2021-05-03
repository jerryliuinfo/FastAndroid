package com.apache.fastandroid.demo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.recycleview.BasicAdapter

/**
 * Created by Jerry on 2021/5/3.
 */
class CommentAdapter: BasicAdapter<String, CommentViewHolder>() {
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(itemView)
    }

    override fun onBindHolder(holder: CommentViewHolder, position: Int) {
        holder.tv_title.text = getData(position)
    }
}



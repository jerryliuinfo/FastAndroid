package com.apache.fastandroid.demo.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R

/**
 * Created by Jerry on 2021/5/3.
 */
class CommentViewHolder(val rootView:View): RecyclerView.ViewHolder(rootView) {
    val tv_title = rootView.findViewById<TextView>(R.id.tv_title)
}
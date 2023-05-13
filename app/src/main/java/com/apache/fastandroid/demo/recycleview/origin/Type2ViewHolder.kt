package com.apache.fastandroid.demo.recycleview.origin

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.apache.fastandroid.demo.bean.MyData

/**
 * Created by Jerry on 2023/5/11.
 */
class Type2ViewHolder(private val itemView:View):RecyclerView.ViewHolder(itemView) {

    var mTextView: TextView = itemView.findViewById(R.id.tv_title)

    fun bind(data:MyData.DataType2){
        mTextView.text = data.text
    }
}
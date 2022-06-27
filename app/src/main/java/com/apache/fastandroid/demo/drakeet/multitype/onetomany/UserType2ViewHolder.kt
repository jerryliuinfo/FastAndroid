package com.apache.fastandroid.demo.drakeet.multitype.onetomany

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.apache.fastandroid.jetpack.flow.data.bean.User
import com.drakeet.multitype.ItemViewBinder

/**
 * Created by Jerry on 2022/6/27.
 */
class UserType2ViewHolder: ItemViewBinder<Data, UserType2ViewHolder.ViewHolder>() {

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
         var titleView:TextView = itemView.findViewById(android.R.id.title)
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_user_type2,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, item: Data) {
        holder.titleView.text = item.title
    }
}
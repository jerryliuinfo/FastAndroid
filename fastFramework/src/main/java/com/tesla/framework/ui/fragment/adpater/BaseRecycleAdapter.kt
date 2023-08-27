//package com.tesla.framework.ui.fragment.adpater
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.annotation.LayoutRes
//import androidx.recyclerview.widget.RecyclerView
//
///**
// * Created by Jerry on 2023/8/27.
// */
//class BaseRecycleAdapter<T,VH:BaseItemViewHolder<T>>(@LayoutRes private val layoutResId: Int, val datas:MutableList<T>):RecyclerView.Adapter<VH>() {
//    override fun onBindViewHolder(holder: VH, position: Int) {
//        holder.bind(datas[position])
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
//        val itemView = LayoutInflater.from(parent.context).inflate(layoutResId,parent)
//        return BaseItemViewHolder(itemView)
//    }
//
//    override fun getItemCount(): Int {
//        return datas.size
//    }
//
//}
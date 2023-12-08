package com.tesla.framework.ui.fragment.adpater

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class DefaultRecyclerAdapter<T>(var datas: MutableList<T>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int {
        return datas.size
    }

    protected fun item(position: Int): T {
        return datas[position]
    }

    fun updateDatas(datas: MutableList<T>){
        this.datas = datas
    }





}

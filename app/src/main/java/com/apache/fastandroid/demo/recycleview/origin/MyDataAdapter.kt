package com.apache.fastandroid.demo.recycleview.origin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.apache.fastandroid.demo.bean.MyData

/**
 * Created by Jerry on 2023/5/11.
 */
class MyDataAdapter(private val data:List<MyData>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            TYPE_1 -> Type1ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_item_type1,parent,false))
            TYPE_2 -> Type2ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_item_type1,parent,false))
            else -> throw IllegalArgumentException("Invalid view type:${viewType}")
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(data[position]){
            is MyData.DataType1 -> TYPE_1
            is MyData.DataType2 -> TYPE_2
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
        when (item) {
            is MyData.DataType1-> (holder as Type1ViewHolder).bind(item)
            is MyData.DataType2 -> (holder as Type2ViewHolder).bind(item)
        }
    }


    companion object{
        private const val TYPE_1 = 0
        private const val TYPE_2 = 1
    }
}
package com.apache.fastandroid.demo.recycleview.diffcallback

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.demo.recycleview.callback.DiffCallback
import com.apache.fastandroid.demo.recycleview.viewholder.DiffItemViewHolder

/**
 * Created by Jerry on 2023/7/1.
 */
class DiffAdapter: RecyclerView.Adapter<DiffItemViewHolder>() {

    private var mDatas:List<DiffItemBean> = emptyList()

    fun updateDatas(datas:List<DiffItemBean>, partyRefresh:Boolean = true){
        if (partyRefresh){
            val diffCallback = DiffCallback(mDatas,datas)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            diffResult.dispatchUpdatesTo(this)

            this.mDatas = datas
        }else{
            this.mDatas = datas
            notifyDataSetChanged()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiffItemViewHolder {
        return DiffItemViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return mDatas.size
    }

    override fun onBindViewHolder(holder: DiffItemViewHolder, position: Int) {
        val item = mDatas[position]
        holder.bind(position,item)
    }
}
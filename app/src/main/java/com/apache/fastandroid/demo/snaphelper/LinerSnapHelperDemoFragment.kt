package com.apache.fastandroid.demo.snaphelper

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.demo.adapter.StringAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.tesla.framework.ui.fragment.recycleview.BaseBRVHFragment


class LinerSnapHelperDemoFragment: BaseBRVHFragment<String, BaseViewHolder>(){

    override fun onApplyRecycleView(recyclerView: RecyclerView) {
        super.onApplyRecycleView(recyclerView)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
            LinearSnapHelper().attachToRecyclerView(this)
        }
    }

    override fun getDatas(): MutableList<String> {
        return Array(100){
            "item:$it"
        }.toMutableList()
    }

    override fun myAdapter(): BaseQuickAdapter<String, BaseViewHolder> {
        return StringAdapter(emptyList())
    }

}
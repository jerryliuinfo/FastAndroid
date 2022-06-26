package com.apache.fastandroid.demo.snaphelper

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.demo.adapter.StringAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.tesla.framework.ui.fragment.BaseBRVHFragment

/**
 * Created by Jerry on 2021/8/20.
 * 用于辅助RecyclerView在滚动结束时将Item对齐到某个位置。特别是列表横向滑动时，
 * 很多时候不会让列表滑到任意位置，而是会有一定的规则限制，这时候就可以通过SnapHelper来定义对齐规则了
 */
class NoSnapHelperDemoFragment: BaseBRVHFragment<String,BaseViewHolder>(){

    override fun myAdapter(): BaseQuickAdapter<String, BaseViewHolder> {
        return StringAdapter(emptyList())
    }


    override fun onApplyRecycleView(recyclerView: RecyclerView) {
        super.onApplyRecycleView(recyclerView)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
        }
    }

    override fun getDatas(): MutableList<String> {
        return Array(100){
            "item:$it"
        }.toMutableList()
    }



}
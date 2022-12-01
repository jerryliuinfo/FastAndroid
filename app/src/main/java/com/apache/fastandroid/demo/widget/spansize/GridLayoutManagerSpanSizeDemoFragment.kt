package com.apache.fastandroid.demo.widget.spansize

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import com.apache.fastandroid.R
import com.tesla.framework.databinding.FragmentRecycleview2Binding
import com.tesla.framework.ui.fragment.BaseBindingFragment
import masteryi.me.mergeadapterdemo.ItemAdapter

/**
 * Created by Jerry on 2022/6/26.
 */
class GridLayoutManagerSpanSizeDemoFragment:BaseBindingFragment<FragmentRecycleview2Binding>(FragmentRecycleview2Binding::inflate) {
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        val mySpanSizeLookup = object :GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                //每一行显示的item个数 = SpanCount / SpanSize
                return when (position) {
                    0 -> {
                        SPAN_COUNT
                    }
                    5 -> {
                        SPAN_COUNT
                    }
                    else -> {
                        1
                    }
                }
            }

        }
        val myLayoutManager = GridLayoutManager(requireContext(), SPAN_COUNT).apply {
            spanSizeLookup = mySpanSizeLookup
        }
        val space = resources.getDimensionPixelSize(R.dimen.normal_space)
        mBinding.recyclerView.apply {
            layoutManager = myLayoutManager
            adapter = ItemAdapter(requireContext())
            addItemDecoration(PostItemDecoration(space,mySpanSizeLookup))
        }

    }

    companion object{
        private const val SPAN_COUNT = 2
    }
}
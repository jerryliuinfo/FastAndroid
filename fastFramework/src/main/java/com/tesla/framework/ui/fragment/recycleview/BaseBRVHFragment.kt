package com.tesla.framework.ui.fragment.recycleview

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.databinding.FragmentRecycleview2Binding
import com.tesla.framework.ui.fragment.BaseBindingFragment


/**
 * Fragment基类
 */

abstract class BaseBRVHFragment<T,VH : BaseViewHolder>:
    BaseBindingFragment<FragmentRecycleview2Binding>(FragmentRecycleview2Binding::inflate) {

    var mAdapter:BaseQuickAdapter<T,VH> ?= null

    abstract fun getDatas():MutableList<T>

    open fun onApplyRecycleView(recyclerView: RecyclerView){

    }


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        mBinding.recyclerView.apply {
            onApplyRecycleView(this)
            adapter = getMyAdapter()
            Logger.d("mAdapter1: $mAdapter")
        }
        mAdapter?.apply {
            setNewInstance(getDatas())
        }
        Logger.d("mAdapter2: $mAdapter")


        setHasOptionsMenu(true)
    }

    abstract fun myAdapter():BaseQuickAdapter<T,VH>


    fun getMyAdapter():BaseQuickAdapter<T,VH>{
        return mAdapter ?: kotlin.run {
            myAdapter().also {
                mAdapter = it
            }
        }
    }
}
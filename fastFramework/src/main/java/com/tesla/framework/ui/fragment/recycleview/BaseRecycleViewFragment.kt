package com.tesla.framework.ui.fragment.recycleview

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.tesla.framework.databinding.FragmentComRecycleviewBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment
import com.tesla.framework.ui.fragment.adpater.DefaultRecyclerAdapter

/**
 * Created by Jerry on 2023/11/26.
 * RecycleView 中的局部刷新机制
 * https://mp.weixin.qq.com/s/Qi4sxUuc4Zi7zNrGqp9-mw
 */
abstract class BaseRecycleViewFragment<T> :
    BaseBindingFragment<FragmentComRecycleviewBinding>(FragmentComRecycleviewBinding::inflate) {

    private lateinit var mRecycleView: RecyclerView

    private lateinit var mAdapter: DefaultRecyclerAdapter<T>

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
        mRecycleView = mBinding.recyclerView

        mRecycleView.apply {
            adapter = getRecycleViewAdapter().also {
                mAdapter = it
            }
        }

    }


    fun getAdapter():DefaultRecyclerAdapter<T>{
        return mAdapter
    }

    abstract fun getRecycleViewAdapter(): DefaultRecyclerAdapter<T>


}
package com.apache.fastandroid.demo.recycleview.origin

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.demo.bean.MyData
import com.tesla.framework.databinding.FragmentComRecycleviewBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2023/5/10.
 * 单 bean 类型对应多样 item。
 */
class RecycleViewOriginFragment:BaseBindingFragment<FragmentComRecycleviewBinding>(FragmentComRecycleviewBinding::inflate) {
    private var mAdapter:MyDataAdapter ?= null



    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        originUsage()

    }

    private fun originUsage() {
        if (mAdapter == null){
            val data1 = Array<MyData.DataType1>(10) {
                MyData.DataType1("数据类型1:${it}")
            }
            val data2 = Array<MyData.DataType1>(10) {
                MyData.DataType1("数据类型2:${it}")
            }
            mAdapter = MyDataAdapter(data1.plus(data2).toList())
        }
        mBinding.recyclerView.adapter = mAdapter

    }



}
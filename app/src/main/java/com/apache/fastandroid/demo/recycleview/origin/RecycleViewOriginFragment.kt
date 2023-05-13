package com.apache.fastandroid.demo.recycleview.origin

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentComRecycleviewBinding
import com.apache.fastandroid.demo.bean.MyData
import com.apache.fastandroid.demo.recycleview.multiitemtype.bean.ItemBean
import com.apache.fastandroid.demo.recycleview.multiitemtype.item.ItemType00
import com.apache.fastandroid.demo.recycleview.multiitemtype.item.ItemType01
import com.apache.fastandroid.demo.recycleview.multiitemtype.item.ItemType02
import com.tencent.lib.multi.MultiAdapter
import com.tesla.framework.ui.fragment.BaseBindingFragment
import java.util.ArrayList

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
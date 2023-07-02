package com.apache.fastandroid.demo.recycleview.diffcallback

/**
 * Created by Jerry on 2023/7/1.
 */


import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentRecycleviewBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment

class DifftemCallbackDemoFragment : BaseBindingFragment<FragmentRecycleviewBinding>(FragmentRecycleviewBinding::inflate) {


    val oldList = listOf(
        DiffItemBean(1, "Apple"),
        DiffItemBean(2, "Banana"),
        DiffItemBean(3, "Orange"),
        DiffItemBean(4, "Orange2")
    )

    val newList = listOf(
        DiffItemBean(1, "Apple"),
        DiffItemBean(2, "Banana"),
        DiffItemBean(4, "Orange"),
        DiffItemBean(5, "Grapes")
    )


    private lateinit var mAdapter:DiffAdapter

     override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

         mBinding.recyclerView.apply {
             adapter = DiffAdapter().also {
                 mAdapter = it
             }
             mAdapter.updateDatas(oldList)
         }

         mBinding.btnRefresh.setOnClickListener {
             mAdapter.updateDatas(newList)
         }
         mBinding.btnRefreshNotifyAll.setOnClickListener {
             mAdapter.updateDatas(newList,false)
         }


     }
}
package com.apache.fastandroid.demo.recycleview.listadapter

/**
 * Created by Jerry on 2023/7/1.
 */


import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.doOnNextLayout
import com.apache.fastandroid.databinding.FragmentRecycleviewBinding
import com.apache.fastandroid.demo.recycleview.bean.DiffItemBean
import com.tesla.framework.ui.fragment.BaseBindingFragment
import java.util.concurrent.TimeUnit

class ListAdapterDemoFragment : BaseBindingFragment<FragmentRecycleviewBinding>(FragmentRecycleviewBinding::inflate) {


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


    private lateinit var mAdapter: MyListAdapter

     override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

         mBinding.recyclerView.apply {
             adapter = MyListAdapter().apply {
                 mAdapter = this
                 submitList(oldList)
//                 doOnNextLayout {
//                     submitList(oldList)
//                     doOnNextLayout {
//                         startPostponedEnterTransition()
//                     }
//                 }
             }

         }
         postponeEnterTransition(1000L, TimeUnit.MILLISECONDS)


         mBinding.btnRefresh.setOnClickListener {
             mAdapter.submitList(newList)
         }

     }
}
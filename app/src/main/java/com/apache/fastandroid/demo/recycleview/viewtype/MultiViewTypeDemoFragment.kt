package com.apache.fastandroid.demo.recycleview.viewtype

/**
 * Created by Jerry on 2023/7/1.
 */


import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentRecycleviewBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * 不同的 bean 展示不同的样式
 * @property mAdapter NavigationAdapter
 */
class MultiViewTypeDemoFragment : BaseBindingFragment<FragmentRecycleviewBinding>(FragmentRecycleviewBinding::inflate) {

    private lateinit var mAdapter: NavigationAdapter

     override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

         mBinding.recyclerView.apply {
             adapter = NavigationAdapter().also {
                 mAdapter = it
             }
         }
         NavigationModel.navigationList.observe(viewLifecycleOwner) {
             mAdapter.submitList(it)
         }
         NavigationModel.setNavigationMenuItemChecked(0)
     }
}
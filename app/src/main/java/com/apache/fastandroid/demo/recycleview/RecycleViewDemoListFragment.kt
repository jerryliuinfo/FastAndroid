package com.apache.fastandroid.demo.recycleview

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentRecycleviewDemoBinding
import com.apache.fastandroid.demo.recycleview.diffcallback.AsyncListDifferDemoFragment
import com.apache.fastandroid.demo.recycleview.diffcallback.DifftemCallbackDemoFragment
import com.apache.fastandroid.demo.recycleview.itemtouch.ItemTouchHelperDemoFragment
import com.apache.fastandroid.demo.recycleview.listadapter.ListAdapterDemoFragment
import com.apache.fastandroid.demo.recycleview.multiitemtype.MultiItemTypeDemoFragment1
import com.apache.fastandroid.demo.recycleview.multiitemtype.MultiItemTypeDemoFragment2
import com.apache.fastandroid.demo.recycleview.origin.MyDataAdapter
import com.apache.fastandroid.demo.recycleview.origin.RecycleViewOriginFragment
import com.apache.fastandroid.demo.recycleview.viewtype.MultiViewTypeDemoFragment
import com.apache.fastandroid.demo.recycleview.zhy.BaseAdapterMultiItemDemoFragment
import com.apache.fastandroid.demo.recycleview.zhy.BaseAdapterRecycleViewDemoFragment
import com.tesla.framework.kt.showToast
import com.tesla.framework.ui.activity.BaseVBFragment2
import com.tesla.framework.ui.activity.FragmentContainerActivity

/**
 * Created by Jerry on 2023/5/10.
 */
class RecycleViewDemoListFragment:BaseVBFragment2<FragmentRecycleviewDemoBinding>() {

    private var mAdapter:MyDataAdapter ?= null


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnRvMultiItemtype.setOnClickListener {
            showToast("1111")

            FragmentContainerActivity.launch(requireActivity(),MultiItemTypeDemoFragment1::class.java, addTitleBar = false)
        }

        mBinding.btnRvMultiItemtype2.setOnClickListener {
            FragmentContainerActivity.launch(requireActivity(), MultiItemTypeDemoFragment2::class.java, addTitleBar = false)
        }

        mBinding.btnRvOrigin.setOnClickListener {
            FragmentContainerActivity.launch(requireActivity(), RecycleViewOriginFragment::class.java, addTitleBar = false)
        }
        mBinding.btnBaseAdapter.setOnClickListener {
            FragmentContainerActivity.launch(requireActivity(), BaseAdapterRecycleViewDemoFragment::class.java, addTitleBar = false)
        }
        mBinding.btnBaseAdapterMultiType.setOnClickListener {
            FragmentContainerActivity.launch(requireActivity(), BaseAdapterMultiItemDemoFragment::class.java, addTitleBar = false)
        }

        mBinding.btnDiffUtilItemCallback.setOnClickListener {
            FragmentContainerActivity.launch(requireActivity(), DifftemCallbackDemoFragment::class.java, addTitleBar = false)
        }

        mBinding.btnAsyncListDiffer.setOnClickListener {
            FragmentContainerActivity.launch(requireActivity(), AsyncListDifferDemoFragment::class.java, addTitleBar = false)
        }

        mBinding.btnListAdapter.setOnClickListener {
            FragmentContainerActivity.launch(requireActivity(), ListAdapterDemoFragment::class.java, addTitleBar = false)
        }
        mBinding.btnMultiViewType.setOnClickListener {
            FragmentContainerActivity.launch(requireActivity(), MultiViewTypeDemoFragment::class.java, addTitleBar = false)
        }
        mBinding.btnMultiViewTypeSameBean.setOnClickListener {
            FragmentContainerActivity.launch(requireActivity(), MultiViewTypeDemoFragment::class.java, addTitleBar = false)
        }

        mBinding.btnItemTouchHelper.setOnClickListener {
            FragmentContainerActivity.launch(requireActivity(), ItemTouchHelperDemoFragment::class.java, addTitleBar = false)
        }


    }



}
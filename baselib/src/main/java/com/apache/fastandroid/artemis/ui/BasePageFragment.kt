package com.apache.fastandroid.artemis.ui

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.artemis.ui.adapter.PageAdapter
import com.apache.fastandroid.artemis.ui.bean.PageModel
import com.tesla.framework.databinding.FragmentPracticeDemoBinding
import com.tesla.framework.ui.fragment.BaseVMFragment

/**
 * Created by Jerry on 2022/3/8.
 */
abstract class BasePageFragment : BaseVMFragment<FragmentPracticeDemoBinding>(FragmentPracticeDemoBinding::inflate){

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
        viewBinding.pager.let {
            it.adapter = PageAdapter(loadPageModels(),requireActivity().supportFragmentManager)
            viewBinding.tabLayout.setupWithViewPager(it)
        }
    }

    abstract fun loadPageModels():MutableList<PageModel>
}
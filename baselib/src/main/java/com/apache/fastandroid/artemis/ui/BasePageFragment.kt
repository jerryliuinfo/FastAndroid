package com.apache.fastandroid.artemis.ui

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.artemis.ui.adapter.PageAdapter
import com.apache.fastandroid.artemis.ui.bean.PageModel
import com.tesla.framework.databinding.FragmentPracticeDemoBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2022/3/8.
 */
abstract class BasePageFragment : BaseBindingFragment<FragmentPracticeDemoBinding>(FragmentPracticeDemoBinding::inflate){

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
        mBinding.pager.let {
            it.adapter = PageAdapter(loadPageModels(),requireActivity().supportFragmentManager)
            mBinding.tabLayout.setupWithViewPager(it)
        }
    }

    abstract fun loadPageModels():MutableList<PageModel>
}
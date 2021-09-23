package com.apache.fastandroid.demo.databinding.practice

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.BR
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentDatabindingViewmodelBinding
import com.apache.fastandroid.demo.databinding.UserViewModel
import com.tesla.framework.support.bean.DataBindingConfig
import com.tesla.framework.ui.fragment.BaseDatebindingFragment


class DatabinDingViewModelFragment: BaseDatebindingFragment<FragmentDatabindingViewmodelBinding>() {

    companion object{
        private const val TAG = "DatabinDingViewModelFragment"
    }

    private lateinit var viewModel:UserViewModel

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        //要使用LiveData对象作为数据绑定来源，需要设置LifecycleOwner, 这样当livedata数据变化后，xml就能察觉到
        binding.lifecycleOwner = this


    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_databinding_viewmodel,BR.vm,viewModel)
    }

    override fun initViewModel() {
        viewModel = getFragmentScopeViewModel(UserViewModel::class.java)
    }
}
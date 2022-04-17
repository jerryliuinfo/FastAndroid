package com.apache.fastandroid.demo.databinding.practice

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentDatabindingViewmodelBinding
import com.apache.fastandroid.demo.databinding.UserViewModel
import com.tesla.framework.ui.fragment.BaseDBFragment


class DatabinDingViewModelFragment: BaseDBFragment<FragmentDatabindingViewmodelBinding>(FragmentDatabindingViewmodelBinding::inflate) {

    companion object{
        private const val TAG = "DatabinDingViewModelFragment"
    }

    private lateinit var viewModel:UserViewModel

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        //要使用LiveData对象作为数据绑定来源，需要设置LifecycleOwner, 这样当livedata数据变化后，xml就能察觉到
        viewBinding.lifecycleOwner = this


    }



    override fun initViewModel() {
        viewModel = getFragmentScopeViewModel(UserViewModel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_databinding_viewmodel
    }
}
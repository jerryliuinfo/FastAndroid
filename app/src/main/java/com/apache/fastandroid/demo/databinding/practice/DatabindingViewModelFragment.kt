package com.apache.fastandroid.demo.databinding.practice

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.apache.fastandroid.databinding.FragmentDatabindingViewmodelBinding
import com.apache.fastandroid.demo.databinding.data.ProfileLiveDataViewModel
import com.tesla.framework.ui.fragment.BaseDataBindingFragment


class DatabindingViewModelFragment :
    BaseDataBindingFragment<FragmentDatabindingViewmodelBinding>(FragmentDatabindingViewmodelBinding::inflate) {
    companion object {
        private const val TAG = "ViewModelFragment"
    }
    private val mViewModel:ProfileLiveDataViewModel by viewModels()


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
        mBinding.apply {
            viewmodel = mViewModel
            //一定要加这句，否则 livedata 数据 在 xml 无法映射
            lifecycleOwner = this@DatabindingViewModelFragment
        }
        mBinding.viewmodel = mViewModel

    }





}
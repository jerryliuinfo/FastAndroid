package com.apache.fastandroid.demo.databinding.practice

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.BR

import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentDatabindingTwoewayBindBinding
import com.apache.fastandroid.demo.bean.UserObservableBean
import com.apache.fastandroid.demo.databinding.UserViewModel
import com.tesla.framework.support.bean.DataBindingConfig
import com.tesla.framework.ui.fragment.BaseDatebindingFragment
import kotlinx.android.synthetic.main.fragment_databinding_twoeway_bind.*


class DatabinDingTwoWayDemoFragment: BaseDatebindingFragment<FragmentDatabindingTwoewayBindBinding>() {
    private lateinit var user:UserObservableBean
    private lateinit var viewModel:UserViewModel
    companion object{
        private const val TAG = "DatabinDingBasicDemo"
    }


    override fun inflateContentView(): Int {
        return R.layout.fragment_databinding_twoeway_bind
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        user = UserObservableBean()
        binding.user = user

        btn_modify_name.setOnClickListener {
            user.name = "zhangsan"
            user.degree.set("Hello")
        }
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_databinding_twoeway_bind, BR.vm,viewModel)
    }

    override fun initViewModel() {
        viewModel = getFragmentScopeViewModel(UserViewModel::class.java)
    }


}
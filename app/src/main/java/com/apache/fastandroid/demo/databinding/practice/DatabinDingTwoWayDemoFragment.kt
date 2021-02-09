package com.apache.fastandroid.demo.databinding.practice

import android.os.Bundle
import android.view.LayoutInflater

import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentDatabindingTwoewayBindBinding
import com.apache.fastandroid.demo.bean.UserObservableBean
import com.tesla.framework.ui.fragment.ABaseDatabindingFragment
import kotlinx.android.synthetic.main.fragment_databinding_twoeway_bind.*


class DatabinDingTwoWayDemoFragment: ABaseDatabindingFragment<FragmentDatabindingTwoewayBindBinding>() {
    private lateinit var user:UserObservableBean
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



}
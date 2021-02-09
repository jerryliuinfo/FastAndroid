package com.apache.fastandroid.demo.databinding.practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentDatabindingOnewayBindObservableBinding
import com.apache.fastandroid.demo.bean.UserObservableBean
import com.tesla.framework.ui.fragment.ABaseDatabindingFragment


class DatabinDingObservableDemoFragment: ABaseDatabindingFragment<FragmentDatabindingOnewayBindObservableBinding>() {
    companion object{
        private const val TAG = "DatabinDingBasicDemo"
    }

    override fun inflateContentView(): Int {
       return R.layout.fragment_databinding_oneway_bind_observable
    }

    private lateinit var user:UserObservableBean

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        user = UserObservableBean().apply {
            name = "zhangsan"
            age = 10
        }

        binding.user = user

        binding.tvModifyName.setOnClickListener {
            user.apply {
                name = "lisi"
                age =  20
            }
        }

        binding.tvModifyAge.setOnClickListener {
            user.plus()
        }
    }





}
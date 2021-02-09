package com.apache.fastandroid.demo.databinding.practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentDatabindingOnewayBindObservableBinding
import com.apache.fastandroid.databinding.FragmentDatabindingOnewayBindObservableFieldBinding
import com.apache.fastandroid.demo.bean.UserObservableBean
import com.apache.fastandroid.demo.bean.UserObservableFieldBean
import com.tesla.framework.ui.fragment.ABaseDatabindingFragment


class DatabinDingObservableFieldDemoFragment: ABaseDatabindingFragment<FragmentDatabindingOnewayBindObservableFieldBinding>() {
    companion object{
        private const val TAG = "DatabinDingBasicDemo"
    }

    override fun inflateContentView(): Int {
        return R.layout.fragment_databinding_oneway_bind_observable_field
    }
    private lateinit var user:UserObservableFieldBean

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        user = UserObservableFieldBean().apply {
            name.set("zhangsan")
            age.set(19)
        }

        binding.user = user


        binding.tvModifyName.setOnClickListener {
            user!!.apply {
                name.set("lisi")
                age.set(0)
            }
        }

        binding.tvModifyAge.setOnClickListener {
            user!!.plus()
        }
    }





}
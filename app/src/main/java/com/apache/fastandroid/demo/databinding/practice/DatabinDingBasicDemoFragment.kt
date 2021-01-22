package com.apache.fastandroid.demo.databinding.practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.apache.fastandroid.R
import com.apache.fastandroid.artemis.base.BaseDatabindingFragment
import com.apache.fastandroid.artemis.support.bean.User
import com.apache.fastandroid.databinding.FragmentDatabindingBasisUserBinding
import com.apache.fastandroid.demo.bean.UserBean
import com.tesla.framework.common.util.log.NLog
import java.util.Random



class DatabinDingBasicDemoFragment: Fragment() {
    companion object{
        private const val TAG = "DatabinDingBasicDemo"
    }

    lateinit var  binding:FragmentDatabindingBasisUserBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_databinding_basis_user, container, false)
        // 1、对布局需要绑定的内容进行加载
        binding = DataBindingUtil.bind(view)!!

        val user = UserBean("zhangsan",10)
        binding!!.user = user
        return binding.root
    }




}
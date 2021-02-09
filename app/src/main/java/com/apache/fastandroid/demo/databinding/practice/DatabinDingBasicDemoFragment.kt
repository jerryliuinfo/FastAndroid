package com.apache.fastandroid.demo.databinding.practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentDatabindingBasisUserBinding
import com.apache.fastandroid.demo.bean.UserBean
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.fragment.ABaseDatabindingFragment
import java.util.Random



class DatabinDingBasicDemoFragment: ABaseDatabindingFragment<FragmentDatabindingBasisUserBinding>() {
    companion object{
        private const val TAG = "DatabinDingBasicDemo"
    }

    override fun inflateContentView(): Int {
       return R.layout.fragment_databinding_basis_user
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
        val user = UserBean("zhangsan1",18)
        binding.user = user
    }



}
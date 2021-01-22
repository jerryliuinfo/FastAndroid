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
import com.apache.fastandroid.databinding.FragmentDatabindingTwoewayBindBinding
import com.apache.fastandroid.demo.bean.UserBean
import com.apache.fastandroid.demo.bean.UserObservableBean
import com.tesla.framework.common.util.log.NLog
import kotlinx.android.synthetic.main.fragment_databinding_twoeway_bind.*
import java.util.Random



class DatabinDingTwoWayDemoFragment: Fragment() {
    companion object{
        private const val TAG = "DatabinDingBasicDemo"
    }

    private lateinit var user:UserObservableBean

    lateinit var  binding:FragmentDatabindingTwoewayBindBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_databinding_twoeway_bind, container, false)
        // 1、对布局需要绑定的内容进行加载
        binding = DataBindingUtil.bind(view)!!

        user = UserObservableBean()
        binding!!.user = user
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_modify_name.setOnClickListener {
            user.name = "zhangsan"
        }

    }



}
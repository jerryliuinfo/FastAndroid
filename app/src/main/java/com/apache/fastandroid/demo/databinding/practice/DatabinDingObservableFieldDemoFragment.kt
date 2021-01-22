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


class DatabinDingObservableFieldDemoFragment: Fragment() {
    companion object{
        private const val TAG = "DatabinDingBasicDemo"
    }

    lateinit var  binding:FragmentDatabindingOnewayBindObservableFieldBinding
    private lateinit var user:UserObservableFieldBean
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_databinding_oneway_bind_observable_field, container, false)
        // 1、对布局需要绑定的内容进行加载
        binding = DataBindingUtil.bind(view)!!

        user = UserObservableFieldBean().apply {
            name.set("zhangsan")
            age.set(19)
        }

        binding!!.user = user
        init()
        return binding.root
    }

    private fun init(){
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
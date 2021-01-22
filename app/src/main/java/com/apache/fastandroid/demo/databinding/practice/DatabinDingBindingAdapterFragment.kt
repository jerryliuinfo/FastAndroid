package com.apache.fastandroid.demo.databinding.practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentDatabindingBindingAdapterBinding
import com.apache.fastandroid.demo.bean.UserObservableFieldBean


class DatabinDingBindingAdapterFragment: Fragment() {
    companion object{
        private const val TAG = "DatabinDingBindingAdapterFragment"
    }

    lateinit var  binding:FragmentDatabindingBindingAdapterBinding
    private lateinit var user:UserObservableFieldBean
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_databinding_binding_adapter, container, false)
        // 1、对布局需要绑定的内容进行加载
        binding = DataBindingUtil.bind(view)!!

        user = UserObservableFieldBean().apply {
            name.set("zhangsan")
            age.set(19)
            avator.set("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg1.gtimg.com%2Fln%2Fpics%2Fhv1%2F103%2F148%2F1558%2F101346793.jpg&refer=http%3A%2F%2Fimg1.gtimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1613900000&t=ae8a5f08768bfca867b1f483f036b274")
        }

        binding!!.user = user
        init()
        return binding.root
    }

    private fun init(){

    }





}
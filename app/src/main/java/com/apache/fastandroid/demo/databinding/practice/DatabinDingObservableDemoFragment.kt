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


/**
 * Created by Jerry on 2021/1/11.
 * activity 中: 使用 xxBing.inflate
 * val binding: ActivityMainBinding = ActivityMainBinding.inflate(getLayoutInflater())
 * Fragment中:
 *     val listItemBinding = ListItemBinding.inflate(layoutInflater, viewGroup, false)
       val listItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item, viewGroup, false)
 */
class DatabinDingObservableDemoFragment: Fragment() {
    companion object{
        private const val TAG = "DatabinDingBasicDemo"
    }

    lateinit var  binding:FragmentDatabindingOnewayBindObservableBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_databinding_oneway_bind_observable, container, false)
        // 1、对布局需要绑定的内容进行加载
        binding = DataBindingUtil.bind(view)!!

        val user = UserObservableBean().apply {
            name = "zhangsan"
            age = 10
        }

        binding!!.user = user
        init()
        return binding.root
    }

    private fun init(){
        binding.tvModifyName.setOnClickListener {
            binding.user!!.apply {
                name = "lisi"
                age =  20
            }
        }

        binding.tvModifyAge.setOnClickListener {
            binding.user!!.plus()
        }
    }



}
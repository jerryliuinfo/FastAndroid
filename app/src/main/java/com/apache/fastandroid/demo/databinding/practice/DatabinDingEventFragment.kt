package com.apache.fastandroid.demo.databinding.practice

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentDatabindingOnewayBindObservableBinding
import com.apache.fastandroid.databinding.FragmentDatabindingOnewayBindObservableFieldBinding
import com.apache.fastandroid.databinding.FragmentDatabindingTwoewayEventBinding
import com.apache.fastandroid.demo.bean.UserObservableBean
import com.apache.fastandroid.demo.bean.UserObservableFieldBean
import com.tesla.framework.common.util.log.NLog


class DatabinDingEventFragment: Fragment() {
    companion object{
        private const val TAG = "DatabinDingEventFragment"
    }

    lateinit var  binding:FragmentDatabindingTwoewayEventBinding
    private lateinit var user:UserObservableFieldBean
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_databinding_twoeway_event, container, false)
        // 1、对布局需要绑定的内容进行加载
        binding = DataBindingUtil.bind(view)!!

        user = UserObservableFieldBean().apply {
            name.set("zhangsan")
            age.set(19)
        }

        binding!!.user = user
        binding.listener = Listener(user)
        init()
        return binding.root
    }

    private fun init(){

    }


    class Listener(val user:UserObservableFieldBean){
        fun changeAge(){
            user.plus()
        }

        fun afterTextChanged(s: Editable){
            NLog.d(TAG, "afterTextChanged s: %s", s.toString())
            user.name.set(s.toString())
        }
    }



}
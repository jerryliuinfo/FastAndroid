package com.apache.fastandroid.jetpack.lifecycle.handler

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentJetpackLifecycleBinding
import com.tesla.framework.component.lifecycle.LifecycleHandler
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2020/10/31.
 * 不会内存泄漏的Handler
 */
class LifecycleHandlerFragment: BaseBindingFragment<FragmentJetpackLifecycleBinding>(FragmentJetpackLifecycleBinding::inflate) {

    private lateinit var lifecycleHandler:LifecycleHandler


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        lifecycleHandler = LifecycleHandler(this).apply {
            postDelayed({
                Log.d("tag", "10s 后我将会执行")
            },10000)
        }



    }


}
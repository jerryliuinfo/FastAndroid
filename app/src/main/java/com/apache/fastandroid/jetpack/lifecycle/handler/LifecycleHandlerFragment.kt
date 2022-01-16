package com.apache.fastandroid.jetpack.lifecycle.handler

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.component.lifecycle.LifecycleHandler

/**
 * Created by Jerry on 2020/10/31.
 * 不会内存泄漏的Handler
 */
class LifecycleHandlerFragment: BaseStatusFragmentNew() {

    private lateinit var lifecycleHandler:LifecycleHandler

    override fun getLayoutId(): Int {
       return R.layout.fragment_jetpack_lifecycle
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        lifecycleHandler = LifecycleHandler(this)
        lifecycleHandler.postDelayed({
            NLog.d(TAG,"handler post msg")
        },10*1000)



    }


}
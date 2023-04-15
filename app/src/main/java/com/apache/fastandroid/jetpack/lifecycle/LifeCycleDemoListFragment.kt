package com.apache.fastandroid.jetpack.lifecycle

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.databinding.FragmentLifecycleModeListBinding
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.jetpack.lifecycle.customlifecycle.CustomLifecycleOwnerFragment
import com.apache.fastandroid.jetpack.lifecycle.handler.LifecycleHandlerFragment
import com.apache.fastandroid.jetpack.lifecycle.service.JetPackLifeCycleServiceFragment
import com.apache.fastandroid.jetpack.lifecycle.traditional.TraditionalLifeCycleFragment
import com.tesla.framework.component.lifecycle.CustomIntentProvider
import com.tesla.framework.component.lifecycle.LifeCycleIntentReceiver
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2020/10/31.
 */
class LifeCycleDemoListFragment: BaseBindingFragment<FragmentLifecycleModeListBinding>(FragmentLifecycleModeListBinding::inflate) {
//    override fun initDatas(): ArrayList<ViewItemBean> {
//        return arrayListOf(
//                ViewItemBean("传统生命周期监听", "采用手动回调方式", TraditionalLifeCycleFragment::class.java)
//                ,ViewItemBean("LifeCycle应用", "LifeCycle应用", JetPackLifeCycleFragment::class.java)
//                ,ViewItemBean("LifeCycleService", "LifeCycleService", JetPackLifeCycleServiceFragment::class.java)
//                ,ViewItemBean("App进入前后台判断", "App进入前后台判断", AppForeGroundFragment::class.java)
//                ,ViewItemBean("任意一个类监听生命周期", "任意一个类监听生命周期", JetPackListenLifecycleFragment::class.java)
//                ,ViewItemBean("LifeCycleHandler", "不会泄露的Handler", LifecycleHandlerFragment::class.java)
//                ,ViewItemBean("实现自定义 LifecycleOwner", "实现自定义 LifecycleOwner", CustomLifecycleOwnerFragment::class.java)
//        )
//    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnBroadcastReceiver.setOnClickListener {
            broadcastReceiverUsage()
        }
    }

    private fun broadcastReceiverUsage() {
        val lifecycleReceiver = LifeCycleIntentReceiver(requireContext(), CustomIntentProvider()){
            Logger.d("onReceiver ")
        }
    }


}
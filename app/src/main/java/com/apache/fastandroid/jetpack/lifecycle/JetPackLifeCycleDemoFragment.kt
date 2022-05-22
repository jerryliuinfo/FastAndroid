package com.apache.fastandroid.jetpack.lifecycle

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.jetpack.lifecycle.handler.LifecycleHandlerFragment
import com.apache.fastandroid.jetpack.lifecycle.service.JetPackLifeCycleServiceFragment
import com.apache.fastandroid.jetpack.lifecycle.traditional.TraditionalLifeCycleFragment

/**
 * Created by Jerry on 2020/10/31.
 */
class JetPackLifeCycleDemoFragment: BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                ViewItemBean("传统生命周期监听", "采用手动回调方式", TraditionalLifeCycleFragment::class.java)
                ,ViewItemBean("LifeCycle应用", "LifeCycle应用", JetPackLifeCycleFragment::class.java)
                ,ViewItemBean("LifeCycleService", "LifeCycleService", JetPackLifeCycleServiceFragment::class.java)
                ,ViewItemBean("ProcessLifecycleOwner", "App进入前后台判断", JetPackLifeCycleFragment::class.java)
                ,ViewItemBean("任意一个类监听生命周期", "任意一个类监听生命周期", JetPackListenLifecycleFragment::class.java)
                ,ViewItemBean("LifeCycleHandler", "不会泄露的Handler", LifecycleHandlerFragment::class.java)
        )
    }


}
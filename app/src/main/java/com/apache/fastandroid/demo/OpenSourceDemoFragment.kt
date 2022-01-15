package com.apache.fastandroid.demo

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.agentweb.AgentWebDemoListFragment
import com.apache.fastandroid.demo.bestpay.BestPayDemoFragment
import com.apache.fastandroid.demo.component.loadsir.LoadSirDemoListFragment
import com.apache.fastandroid.demo.glide.GlideDemoFragment
import com.apache.fastandroid.demo.list.MultiTypeDeomoFragment
import com.apache.fastandroid.demo.logger.LoggerDemoFragment
import com.apache.fastandroid.demo.rxjava.RxJavaDemoListFragment

/**
 * Created by Jerry on 2020/11/11.
 */
class OpenSourceDemoFragment: BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                ViewItemBean("Glide", "Glide", GlideDemoFragment::class.java)
                ,ViewItemBean("Rxjava", "Rxjava操作符", RxJavaDemoListFragment::class.java)
                ,ViewItemBean("AgentWeb", "AgentWeb", AgentWebDemoListFragment::class.java)
                ,ViewItemBean("AppMananger", "https://github.com/MuntashirAkon/AppManager", AgentWebDemoListFragment::class.java)
                ,ViewItemBean("PermissionX", "PermissionX", PermissionXFragment::class.java)
                ,ViewItemBean("LoadSir", "LoadSir", LoadSirDemoListFragment::class.java)
                ,ViewItemBean("logger", "LoadSir", LoggerDemoFragment::class.java)
                ,ViewItemBean("MultiType", "MultiType", MultiTypeDeomoFragment::class.java)
                ,ViewItemBean("BestPay", "BestPay", BestPayDemoFragment::class.java)

        )
    }


}
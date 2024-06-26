package com.apache.fastandroid.demo.bestpractice

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.bestpractice.network.NetworkDemoFragment
import com.apache.fastandroid.demo.bestpractice.performance.InspectPerformanceDemoFragment
import com.apache.fastandroid.demo.cheese.CheeseActivity
import com.apache.fastandroid.demo.elegant.ElegantDemoFragment

/**
 * Created by Jerry on 2020/11/11.
 */
class BestPracticeDemoFragment: BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
            ViewItemBean("Cheese", "夜间模式最佳实践",null, CheeseActivity::class.java,addTitleBar = false)
            ,ViewItemBean("网络监听", "网络监听",NetworkDemoFragment::class.java)
            ,ViewItemBean("优雅式例代码", "优雅式例代码",ElegantDemoFragment::class.java)
            ,ViewItemBean("ShowCase", "ShowCase",ElegantDemoFragment::class.java)
            ,ViewItemBean("性能观察", "性能观察", InspectPerformanceDemoFragment::class.java)

        )
    }
}
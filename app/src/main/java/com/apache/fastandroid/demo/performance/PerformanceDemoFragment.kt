package com.apache.fastandroid.demo.performance

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.performance.practice.AnrWatchDogDemoFragment

/**
 * Created by Jerry on 2020/11/11.
 */
class PerformanceDemoFragment: BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                ViewItemBean("卡顿监控", "AnrWatchDog", AnrWatchDogDemoFragment::class.java)
        )
    }
}
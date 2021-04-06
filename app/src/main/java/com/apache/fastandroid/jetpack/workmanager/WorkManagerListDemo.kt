package com.apache.fastandroid.jetpack.workmanager

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment

/**
 * Created by Jerry on 2021/4/6.
 */
class WorkManagerListDemo: BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                ViewItemBean("一次性任务", "OneTimeWorkRequest", OneTimeWorkRequestDemoFragment::class.java)
                ,ViewItemBean("周期性任务", "PeriodicWorkRequest", PeriodicWorkRequestDemoFragment::class.java)
                ,ViewItemBean("任务链", "任务链", WorkManagerChainDemoFragment::class.java)

        )
    }

}
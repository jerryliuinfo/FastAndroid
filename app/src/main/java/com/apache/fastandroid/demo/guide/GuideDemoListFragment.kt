package com.apache.fastandroid.demo.guide

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.guide.appdata.AppDataDemoFragment
import com.apache.fastandroid.demo.location.LocationDemoFragment
import com.apache.fastandroid.demo.service.MessengerDemoFragment
import com.apache.fastandroid.demo.service.ServiceDemoFragment
import com.apache.fastandroid.jetpack.alarm.AlarmManagerDemoFragment

/**
 * Created by Jerry on 2020/11/11.
 */
class GuideDemoListFragment : BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
            ViewItemBean(
                "应用基础知识",
                "应用基础知识",
                activity = AppResourceDemoActivity::class.java
            ),
            ViewItemBean("Service", "Service", ServiceDemoFragment::class.java),
            ViewItemBean("Messenger", "Messenger", MessengerDemoFragment::class.java),
            ViewItemBean("应用数据和文件", "应用数据和文件", AppDataDemoFragment::class.java),
            ViewItemBean("用户位置", "用户位置", LocationDemoFragment::class.java),
            ViewItemBean("闹钟", "AlarmManager", AlarmManagerDemoFragment::class.java),
            ViewItemBean("最佳实践", "最佳实践", AlarmManagerDemoFragment::class.java),


        )
    }
}
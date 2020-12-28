package com.apache.fastandroid.demo.doraemonkit

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment

/**
 * Created by Jerry on 2020/11/11.
 */
class DoraemonkitDemoFragment:BaseListFragment() {
    companion object{
        const val TAG = "DoraemonkitDemoFragment"
    }

    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                ViewItemBean("BlockMonitor", "卡顿监控", BlockMonitorFragment::class.java),
                ViewItemBean("TimeCount", "启动耗时", BlockMonitorFragment::class.java)

        )
    }

}
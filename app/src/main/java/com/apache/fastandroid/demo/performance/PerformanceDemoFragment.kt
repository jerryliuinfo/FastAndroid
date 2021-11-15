package com.apache.fastandroid.demo.performance

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.mmkv.MMKVFragment
import com.apache.fastandroid.demo.performance.largeimage.LargeImageDemoFragment
import com.apache.fastandroid.demo.performance.practice.AnrWatchDogDemoFragment

/**
 * Created by Jerry on 2020/11/11.
 */
class PerformanceDemoFragment: BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                ViewItemBean("AnrWatchDog", "AnrWatchDog", AnrWatchDogDemoFragment::class.java)
                ,ViewItemBean("BlockCancary", "BlockCancary", BlockCancaryDemoFragment::class.java)
                ,ViewItemBean("LargetImageMonitor", "大图监控", LargeImageDemoFragment::class.java)
                 ,ViewItemBean("MMKV", "MMKV", MMKVFragment::class.java)
                 ,ViewItemBean("AppFaster", "AppFaster", MMKVFragment::class.java)
                 ,ViewItemBean("TaskDispatcher", "TaskDispatcher", TaskDispatcherDemoFragment::class.java)
        )
    }
}
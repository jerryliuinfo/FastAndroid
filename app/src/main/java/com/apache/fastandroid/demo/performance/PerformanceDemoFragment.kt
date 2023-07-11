package com.apache.fastandroid.demo.performance

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.crashreport.CrashReportDemoFragment
import com.apache.fastandroid.demo.mmkv.MMKVFragment
import com.apache.fastandroid.demo.mmkv.MMKVKtxFragment
import com.apache.fastandroid.demo.performance.fps.FpsDemoFragment
import com.apache.fastandroid.demo.performance.frameanimation.FrameAnimationDemoFragment
import com.apache.fastandroid.demo.performance.frameanimation.FrameAnimationOptimizeDemoFragment
import com.apache.fastandroid.demo.performance.largeimage.LargeImageDemoFragment
import com.apache.fastandroid.demo.performance.practice.AnrWatchDogDemoFragment
import com.apache.fastandroid.demo.slimber.SlimberFragment

/**
 * Created by Jerry on 2020/11/11.
 */
class PerformanceDemoFragment: BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf (
                ViewItemBean("AnrWatchDog", "AnrWatchDog", AnrWatchDogDemoFragment::class.java)
                ,ViewItemBean("BlockCancary", "BlockCancary", BlockCancaryDemoFragment::class.java)
                ,ViewItemBean("LargetImageMonitor", "大图监控", LargeImageDemoFragment::class.java)
                 ,ViewItemBean("Slimber", "Slimber", SlimberFragment::class.java)

                 ,ViewItemBean("TaskDispatcher", "TaskDispatcher", TaskDispatcherDemoFragment::class.java)
                 ,ViewItemBean("OMagnifier", "OMagnifier", OMagnifierDemoFragment::class.java)
                 ,ViewItemBean("Cockroach", "Cockroach", CockroachDemoFragment::class.java)
                 ,ViewItemBean("CrashReport", "CrashReport", CrashReportDemoFragment::class.java)
                 ,ViewItemBean("Takt", "监听fps", FpsDemoFragment::class.java)
                 ,ViewItemBean("帧动画原始", "帧动画原始", FrameAnimationDemoFragment::class.java)
                 ,ViewItemBean("帧动画性能优化", "帧动画性能优化", FrameAnimationOptimizeDemoFragment::class.java)
        )
    }
}
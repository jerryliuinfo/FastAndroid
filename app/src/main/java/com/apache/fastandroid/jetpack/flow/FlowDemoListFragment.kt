package com.apache.fastandroid.jetpack.flow

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.jetpack.flow.completion.CompletionFragment
import com.apache.fastandroid.jetpack.flow.errorhandling.catch.CatchFragment
import com.apache.fastandroid.jetpack.flow.errorhandling.emitall.EmitAllFragment
import com.apache.fastandroid.jetpack.flow.filter.FilterFragment
import com.apache.fastandroid.jetpack.flow.map.MapFragment
import com.apache.fastandroid.jetpack.flow.parallel.ParallelNetworkFragment
import com.apache.fastandroid.jetpack.flow.practice.FlowDownloadFragment
import com.apache.fastandroid.jetpack.flow.practice.FlowRetrofitFragment
import com.apache.fastandroid.jetpack.flow.practice.FlowUserInfoFragment
import com.apache.fastandroid.jetpack.flow.retry.RetryFragment
import com.apache.fastandroid.jetpack.flow.retryexponentialbackoff.RetryExponentialbackoffFragment
import com.apache.fastandroid.jetpack.flow.retrywhen.RetryWhenFragment
import com.apache.fastandroid.jetpack.flow.room.RoomDbFragment
import com.apache.fastandroid.jetpack.flow.search.SearchFragment
import com.apache.fastandroid.jetpack.flow.serias.SerialNetworkFragment
import com.apache.fastandroid.jetpack.flow.single.SingleNetworkFragment
import com.apache.fastandroid.jetpack.flow.task.onetask.LongRunningTaskFragment
import com.apache.fastandroid.jetpack.flow.task.twotasks.TwoLongRunningTaskFragment

/**
 * Created by Jerry on 2020/10/31.
 * https://github.com/MindorksOpenSource/Kotlin-Flow-Android-Examples
 */
class FlowDemoListFragment: BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
            ViewItemBean("Flow基本使用","Flow基本使用", FlowDemoFragment::class.java)
            ,ViewItemBean("Flow基本使用2","Flow基本使用2", FlowBasicUsageFragment::class.java)
            ,ViewItemBean("Single Network Call","Single Network Call", SingleNetworkFragment::class.java)
            ,ViewItemBean("Series Network Calls","Series Network Calls", SerialNetworkFragment::class.java)
            ,ViewItemBean("Parallel Network Calls","Parallel Network Calls", ParallelNetworkFragment::class.java)
            ,ViewItemBean("Room Database","Room Database", RoomDbFragment::class.java)
            ,ViewItemBean("Catch Error","Catch Error", CatchFragment::class.java)
            ,ViewItemBean("EmitAll Error","EmitAll Error", EmitAllFragment::class.java)
            ,ViewItemBean("Completion","Completion", CompletionFragment::class.java)
            ,ViewItemBean("Long Running Task","Long Running Task", LongRunningTaskFragment::class.java)
            ,ViewItemBean("Two Long Running Tasks","Two Long Running Tasks", TwoLongRunningTaskFragment::class.java)
            ,ViewItemBean("Filter","Filter", FilterFragment::class.java)
            ,ViewItemBean("Map","Map", MapFragment::class.java)
            ,ViewItemBean("Search","Search", SearchFragment::class.java)
            ,ViewItemBean("Retry","Retry", RetryFragment::class.java)
            ,ViewItemBean("RetryWhen","RetryWhen", RetryWhenFragment::class.java)
            ,ViewItemBean("RetryExponentialBackoff","RetryExponentialBackoff", RetryExponentialbackoffFragment::class.java)
            ,ViewItemBean("Flow应用实践","Flow应用实践", FlowPracticeDemoFragment::class.java)
            ,ViewItemBean("文件下载","文件下载", FlowDownloadFragment::class.java)
            ,ViewItemBean("Flow和 Room 结合","Flow和Room结合", FlowUserInfoFragment::class.java)
            ,ViewItemBean("Flow和 Retrofit 结合","Flow和 Retrofit 结合", FlowRetrofitFragment::class.java)

        )
    }


}
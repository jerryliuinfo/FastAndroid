package com.apache.fastandroid.jetpack.flow

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.jetpack.flow.completion.CompletionFragment
import com.apache.fastandroid.jetpack.flow.errorhandling.catch.CatchFragment
import com.apache.fastandroid.jetpack.flow.errorhandling.emitall.EmitAllFragment
import com.apache.fastandroid.jetpack.flow.parallel.ParallelNetworkFragment
import com.apache.fastandroid.jetpack.flow.room.RoomDbFragment
import com.apache.fastandroid.jetpack.flow.serias.SerialNetworkFragment
import com.apache.fastandroid.jetpack.flow.single.SingleNetworkFragment
import com.apache.fastandroid.jetpack.flow.task.onetask.LongRunningTaskFragment

/**
 * Created by Jerry on 2020/10/31.
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

        )
    }


}
package com.apache.fastandroid.demo.performance.jankstats

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.databinding.FragmentRecycleviewBinding
import com.apache.fastandroid.demo.bean.MyData
import com.apache.fastandroid.demo.recycleview.origin.MyDataAdapter
import com.tesla.framework.ui.activity.BaseBindingActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor

/**
 * Created by Jerry on 2023/7/15.
 */
class JankLoggingActivity:BaseBindingActivity<FragmentRecycleviewBinding>() {

//    private lateinit var jankStats: JankStats
//
//    // [START jank_frame_listener]
//    private val jankFrameListener = JankStats.OnFrameListener { frameData ->
//        // A real app could do something more interesting, like writing the info to local storage and later on report it.
//        Log.v("JankStatsSample", frameData.toString())
//    }
//    private  var metricsStateHolder:PerformanceMetricsState.MetricsStateHolder ?= null

    val datas = Array<MyData.DataType1>(20) {
        MyData.DataType1("Message #:${it}")
    }

//    private val scrollListener = object : RecyclerView.OnScrollListener() {
//        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//            // check if JankStats is initialized and skip adding state if not
//            val metricsState = metricsStateHolder?.state ?: return
//
//            when (newState) {
//                RecyclerView.SCROLL_STATE_DRAGGING -> {
//                    metricsState.addState("RecyclerView", "Dragging")
//                }
//                RecyclerView.SCROLL_STATE_SETTLING -> {
//                    metricsState.addState("RecyclerView", "Settling")
//                }
//                else -> {
//                    metricsState.removeState("RecyclerView")
//                }
//            }
//        }
//    }


    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)


        // metrics state holder can be retrieved regardless of JankStats initialization
        /*metricsStateHolder = PerformanceMetricsState.getForHierarchy(mBinding.root)

        // initialize JankStats for current window
        jankStats = JankStats.createAndTrack(
            window,
            Dispatchers.Default.asExecutor(),
            jankFrameListener,
        )

        // add activity name as state
        metricsStateHolder?.state?.addState("Activity", javaClass.simpleName)*/


        mBinding.recyclerView.adapter = MyDataAdapter(datas.toList())

//        mBinding.recyclerView.addOnScrollListener(scrollListener)


    }

    override fun onResume() {
        super.onResume()
//        jankStats.isTrackingEnabled = true
    }

    override fun onPause() {
        super.onPause()
//        jankStats.isTrackingEnabled = false
    }

    override fun onDestroy() {
        super.onDestroy()
//        metricsStateHolder = null
    }
}
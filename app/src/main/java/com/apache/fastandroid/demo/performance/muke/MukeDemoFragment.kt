package com.apache.fastandroid.demo.performance.muke

import android.os.Bundle
import android.os.Debug
import android.view.LayoutInflater
import androidx.core.os.TraceCompat
import com.apache.fastandroid.databinding.FragmentMukePerformanceBinding
import com.optimize.performance.launchstarter.TaskDispatcher
import com.tesla.framework.common.util.LaunchTimer
import com.tesla.framework.ui.fragment.BaseBindingFragment
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Created by Jerry on 2024/4/20.
 */
class MukeDemoFragment:BaseBindingFragment<FragmentMukePerformanceBinding>(FragmentMukePerformanceBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnAsyncOptimize.setOnClickListener {
            aysncOptimize()
        }

        mBinding.btnStartupOptimize.setOnClickListener {
            startupOptimize()
        }

        mBinding.btnTraceView.setOnClickListener {
            traceViewUsage()
        }

        mBinding.btnSystrace.setOnClickListener {
            systraceUsage()
        }
    }

    private fun aysncOptimize() {
        val dispatcher = TaskDispatcher.createInstance()

        dispatcher
            .addTask(InitTask(InitTask.TASK1))
            .addTask(InitTask(InitTask.TASK2))
            .addTask(InitTask(InitTask.TASK3))
            .addTask(InitTask(InitTask.TASK4))
            .addTask(InitTask(InitTask.TASK5))
            .start()

        dispatcher.await()
    }

    /**
     * 启动优化介绍
     */
    private fun startupOptimize() {

        // 启动时间测量方式
        measureAppStartTimeByAdb()
        measureAppStartTimeByBuryPoint()

        // 优雅获取方法耗时

    }

    private fun measureAppStartTimeByBuryPoint() {
        LaunchTimer.startRecord ()
        LaunchTimer.endRecord("appStart")

        // 误区：onWindowFocusChange 只是首帧时间，并不是代表界面已经展示出来了

        // 正解: Feed 第一条展示
    }

    private fun measureAppStartTimeByAdb() {
        val command = "adb shell am start -W com.apache.fastandroid/com.apache.fastandroid.MainActivity"
    }

    /**
     * 生成的文件在 sdcard/Android/data/packagename/files
     */
    private fun traceViewUsage(){
        // 将时间作为文件名，避免被覆盖
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        Debug.startMethodTracing(dateFormat.format(Date()))

        Debug.stopMethodTracing()
    }


    private fun systraceUsage(){
        try {
            TraceCompat.beginSection("Section1")
            Thread.sleep(500)
        } finally {
            TraceCompat.endSection()
        }

        try {
            TraceCompat.beginSection("Section2")
            Thread.sleep(300)
        } finally {
            TraceCompat.endSection()
        }


    }


    /**
     * 异步优化
     * 核心思想：主线程分担子线程任务，并行减少时间
     */
    private fun asyncOptimize(){

    }
}
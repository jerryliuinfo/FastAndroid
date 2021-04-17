package com.apache.fastandroid.task

import android.app.Application
import com.github.moduth.blockcanary.BlockCanary
import com.github.moduth.blockcanary.BlockCanaryContext
import com.optimize.performance.launchstarter.task.Task
import com.squareup.leakcanary.LeakCanary
import com.tesla.framework.common.util.log.NLog

/**
 * Created by Jerry on 2021/4/17.
 */
class PerformanceTask(val application:Application):Task(){
    override fun run() {
        NLog.d("task", "PerformanceTask run --->")
        LeakCanary.install(application)

        BlockCanary.install(application.applicationContext, BlockCanaryContext()).start()
    }

}
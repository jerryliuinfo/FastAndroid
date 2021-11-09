package com.apache.fastandroid.task

import android.app.Application
import android.text.TextUtils
import com.apache.fastandroid.imageloader.GlideImageLoader
import com.github.moduth.blockcanary.BlockCanary
import com.github.moduth.blockcanary.BlockCanaryContext
import com.optimize.performance.launchstarter.task.Task
import com.squareup.leakcanary.LeakCanary
import com.tesla.framework.applike.FApplication
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.component.imageloader.IImageLoaderstrategy
import com.tesla.framework.component.imageloader.ImageLoaderManager
import java.util.concurrent.ThreadLocalRandom

/**
 * Created by Jerry on 2021/4/17.
 */
class PerformanceTask(val application:Application):Task(){
    override fun run() {
       start()
    }

    fun start(){
        NLog.d("task", "PerformanceTask run --->")
        LeakCanary.install(application)
        Thread.sleep(200 )
    }
}
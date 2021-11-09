package com.apache.fastandroid.task

import com.blankj.utilcode.util.Utils
import com.optimize.performance.launchstarter.task.Task
import com.tesla.framework.applike.FApplication
import com.tesla.framework.common.util.log.NLog
import java.util.concurrent.ThreadLocalRandom

/**
 * Created by Jerry on 2021/4/17.
 */
class DoraemonkitTask: Task() {

    override fun run() {
       start()
    }

    fun  start(){
        NLog.d("task", "DoraemonkitTask run --->")
        Utils.init(FApplication.getApplication())
        Thread.sleep(100 )
    }
}
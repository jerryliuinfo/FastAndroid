package com.apache.fastandroid.task

import android.os.SystemClock
import com.optimize.performance.launchstarter.task.Task
import com.tesla.framework.common.util.log.NLog

/**
 * Created by Jerry on 2021/4/17.
 */
class DelayInitTask1:Task() {
    override fun run() {
        SystemClock.sleep(100);
        NLog.d("task", "DelayTask1 run")
    }
}
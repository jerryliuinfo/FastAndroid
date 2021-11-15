package com.apache.fastandroid.performance.startup.dispatcher

import com.apache.fastandroid.performance.TaskUtil
import com.optimize.performance.launchstarter.task.Task
import com.tesla.framework.common.util.log.NLog

/**
 * Created by Jerry on 2021/4/17.
 */
class Task1:Task(),Runnable {

    override fun run() {
        NLog.d("task", "Task1 run on %s thread",Thread.currentThread())
        TaskUtil.runTask1()
    }

    override fun runOnMainThread(): Boolean {
        return false
    }

    override fun dependsOn(): MutableList<Class<out Task>> {
        return mutableListOf(Task2::class.java)
    }
}
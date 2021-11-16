package com.apache.fastandroid.performance.startup.dispatcher

import com.apache.fastandroid.performance.TaskUtil
import com.optimize.performance.launchstarter.task.Task
import com.optimize.performance.launchstarter.utils.DispatcherExecutor
import com.tesla.framework.common.util.log.NLog
import java.util.concurrent.ExecutorService

/**
 * Created by Jerry on 2021/4/17.
 */
class Task2:Task(),Runnable {
    override fun run() {
        NLog.d("task", "Task2 run on %s thread",Thread.currentThread())
        TaskUtil.runTask2()
    }

    override fun dependsOn(): MutableList<Class<out Task>> {
        return mutableListOf(Task1::class.java)
    }

    override fun runOn(): ExecutorService {
        return DispatcherExecutor.getCPUExecutor()
    }


}
package com.apache.fastandroid.performance.startup.dispatcher

import com.apache.fastandroid.performance.TaskUtil
import com.optimize.performance.launchstarter.task.Task
import com.optimize.performance.launchstarter.utils.DispatcherExecutor
import java.util.concurrent.ExecutorService

/**
 * Created by Jerry on 2021/4/17.
 */
class Task3:Task() {
    override fun run() {
        TaskUtil.runTask3()
    }

    override fun runOn(): ExecutorService {
        return DispatcherExecutor.getCPUExecutor()
    }

    override fun dependsOn(): MutableList<Class<out Task>> {
        return mutableListOf(Task1::class.java,Task2::class.java)
    }

}
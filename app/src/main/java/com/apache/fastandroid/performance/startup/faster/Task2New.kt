package com.apache.fastandroid.performance.startup.faster

import com.apache.fastandroid.performance.TaskUtil
import com.optimize.performance.launchstarter.task.Task
import com.optimize.performance.launchstarter.utils.DispatcherExecutor
import com.tesla.framework.common.util.log.NLog
import com.wxy.appstartfaster.executor.TaskExceutorManager
import com.wxy.appstartfaster.task.AppStartTask
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService

/**
 * Created by Jerry on 2021/4/17.
 */
class Task2New: AppStartTask() {
    override fun run() {
        TaskUtil.runTask2()
    }

    override fun isRunOnMainThread(): Boolean {
        return false
    }

    override fun runOnExecutor(): Executor {
        return TaskExceutorManager.getInstance().cpuThreadPoolExecutor
    }

    override fun getDependsTaskList(): MutableList<Class<out AppStartTask>> {
        return mutableListOf(Task1New::class.java)
    }
}
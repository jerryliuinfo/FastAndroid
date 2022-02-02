package com.apache.fastandroid.performance.startup.faster

import com.apache.fastandroid.performance.TaskUtil
import com.wxy.appstartfaster.executor.TaskExceutorManager
import com.wxy.appstartfaster.task.AppStartTask
import java.util.concurrent.Executor

/**
 * Created by Jerry on 2021/4/17.
 */
class Task3New:AppStartTask() {
    override fun run() {
        TaskUtil.runTask3()
    }

    override fun isRunOnMainThread(): Boolean {
        return false
    }

    override fun runOnExecutor(): Executor {
        return TaskExceutorManager.getInstance().cpuThreadPoolExecutor
    }

    override fun getDependsTaskList(): MutableList<Class<out AppStartTask>> {
        return mutableListOf(Task2New::class.java)
    }



}
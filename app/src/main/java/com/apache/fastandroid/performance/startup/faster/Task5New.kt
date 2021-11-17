package com.apache.fastandroid.performance.startup.faster

import com.apache.fastandroid.performance.TaskUtil
import com.optimize.performance.launchstarter.task.Task
import com.wxy.appstartfaster.task.AppStartTask
import java.util.concurrent.Executor

/**
 * Created by Jerry on 2021/4/17.
 */
class Task5New: AppStartTask() {

    override fun run() {
        TaskUtil.runTask5()
    }

    override fun getDependsTaskList(): MutableList<Class<out AppStartTask>> {
        return mutableListOf(Task3New::class.java,Task2New::class.java)
    }

    override fun isRunOnMainThread(): Boolean {
        return false
    }


}
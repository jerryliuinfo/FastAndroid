package com.apache.fastandroid.performance.startup.faster

import com.apache.fastandroid.performance.TaskUtil
import com.optimize.performance.launchstarter.task.Task
import com.wxy.appstartfaster.task.AppStartTask

/**
 * Created by Jerry on 2021/4/17.
 */
class Task4New: AppStartTask() {

    override fun run() {
        TaskUtil.runTask4()
    }

    override fun isRunOnMainThread(): Boolean {
        return false
    }


    override fun getDependsTaskList(): MutableList<Class<out AppStartTask>> {
        return mutableListOf(Task2New::class.java,Task3New::class.java)
    }


}
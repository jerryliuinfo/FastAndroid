package com.apache.fastandroid.performance.startup.faster

import com.apache.fastandroid.performance.TaskUtil
import com.wxy.appstartfaster.task.AppStartTask

/**
 * Created by Jerry on 2021/4/17.
 */
class Task1New: AppStartTask() {
    override fun run() {
        TaskUtil.runTask1()
    }

    override fun isRunOnMainThread(): Boolean {
        return true
    }

}
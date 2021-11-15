package com.apache.fastandroid.performance.startup.faster

import com.apache.fastandroid.performance.TaskUtil
import com.optimize.performance.launchstarter.task.Task

/**
 * Created by Jerry on 2021/4/17.
 */
class Task111:Task() {
    override fun run() {
        TaskUtil.runTask1()
    }

    override fun runOnMainThread(): Boolean {
        return true
    }


}
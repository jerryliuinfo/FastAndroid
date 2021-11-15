package com.apache.fastandroid.performance.startup.dispatcher

import com.apache.fastandroid.performance.TaskUtil
import com.optimize.performance.launchstarter.task.Task

/**
 * Created by Jerry on 2021/4/17.
 */
class Task4: Task() {

    override fun run() {
        TaskUtil.runTask4()
    }


}
package com.apache.fastandroid.performance.startup.dispatcher

import com.apache.fastandroid.performance.TaskUtil
import com.optimize.performance.launchstarter.task.Task

/**
 * Created by Jerry on 2021/4/17.
 */
class Task5: Task() {

    override fun run() {
        TaskUtil.runTask5()
    }

    override fun dependsOn(): MutableList<Class<out Task>> {
        return mutableListOf(Task3::class.java,Task2::class.java)
    }


}
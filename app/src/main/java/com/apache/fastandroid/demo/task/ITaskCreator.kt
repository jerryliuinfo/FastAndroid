package com.apache.fastandroid.demo.task

import com.optimize.performance.launchstarter.task.Task

/**
 * Created by Jerry on 2022/2/3.
 */
interface ITaskCreator {
    fun createTask(taskName:String): Task
}
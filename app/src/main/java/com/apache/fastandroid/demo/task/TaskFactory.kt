package com.apache.fastandroid.demo.task

import com.optimize.performance.launchstarter.task.Task
import java.lang.IllegalArgumentException

/**
 * Created by Jerry on 2022/2/3.
 */
class TaskFactory(val taskCreator: ITaskCreator) {
    private val mTaskMap = mutableMapOf<String,Task>()

    fun getTask(taskName:String):Task {
        var task = mTaskMap.get(taskName)
        if (task != null) {
            return task
        }
        task = taskCreator.createTask(taskName)
        if (task == null) {
            throw IllegalArgumentException("Create task fail, there is no task corresponding to the task name. Make sure you have create a task instance in TaskCreator.")
        }
        mTaskMap[taskName] = task
        return task
    }


}
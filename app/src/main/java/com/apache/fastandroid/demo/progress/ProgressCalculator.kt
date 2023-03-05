package com.apache.fastandroid.demo.progress

/**
 * Created by Jerry on 2023/3/4.
 */
class ProgressCalculator {

    // 当前已经完成的任务数
    private val tasks = mutableListOf<TaskInfo>()



    var mProgress: Int = 0
        get() {
            val result =  tasks.sumOf {
                it.percent
            }
            return result
        }

    fun taskFinish(taskInfo: TaskInfo):Int {
        if (!tasks.contains(taskInfo)) {
            tasks.add(taskInfo)
        }
        return mProgress
    }

    fun clear(){
        tasks.clear()
    }
}
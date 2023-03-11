package com.tesla.framework.component.progress

/**
 * Created by Jerry on 2023/3/4.
 */
class ProgressCalculator: ICalculator {

    // 当前已经完成的任务数
    private val tasks = mutableListOf<ITaskInfo>()

    override fun getProgress(): Int {
        val result =  tasks.sumOf {
            it.weight
        }

        val i = 10
        if (i in 0..20){

        }
        return if (result > 100) 100 else result
    }

    @Synchronized
    override fun addFinshedTask(taskInfo: ITaskInfo):Int {
        tasks.add(taskInfo)
        return getProgress()
    }

    override fun clearTask() {
        tasks.clear()
    }
}
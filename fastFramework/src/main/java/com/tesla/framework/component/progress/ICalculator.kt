package com.tesla.framework.component.progress

/**
 * Created by Jerry on 2023/3/5.
 */
interface ICalculator {

    /**
     * 获取实际任务进度
     * @return Int
     */
    fun getProgress():Int

    /**
     * 某个任务结束的时候调用
     * @param taskInfo TaskInfo
     */
    fun addFinshedTask(taskInfo: ITaskInfo):Int

    /**
     * 结束任务
     */
    fun clearTask()
}
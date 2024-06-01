package com.apache.fastandroid.demo.performance.muke

import com.optimize.performance.launchstarter.task.Task
import com.optimize.performance.launchstarter.utils.DispatcherLog
import kotlin.random.Random

/**
 * Created by Jerry on 2024/6/1.
 */
class InitTask(private val name:String): Task() {

    companion object{
        const val TASK1 = "task1"
        const val TASK2 = "task2"
        const val TASK3 = "task3"
        const val TASK4 = "task4"
        const val TASK5 = "task5"
    }
    override fun run() {
        DispatcherLog.i("$name begin run")
        Thread.sleep(Random.nextInt(1000).toLong())
        DispatcherLog.i("$name run finished")
    }

    override fun needWait(): Boolean {
        return name == TASK3
    }
}
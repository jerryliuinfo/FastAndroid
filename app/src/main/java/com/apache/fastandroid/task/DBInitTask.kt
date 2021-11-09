package com.apache.fastandroid.task

import com.optimize.performance.launchstarter.task.Task
import com.tesla.framework.common.util.log.NLog
import java.util.concurrent.ThreadLocalRandom

/**
 * Created by Jerry on 2021/4/17.
 */
class DBInitTask:Task() {
    override fun run() {
        //初始化db
        start()
    }

    companion object{
        @JvmStatic
        fun start(){
            NLog.d("task", "DBInitTask run --->")
            Thread.sleep(150 )
        }
    }

}
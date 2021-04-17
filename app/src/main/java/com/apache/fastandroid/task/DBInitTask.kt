package com.apache.fastandroid.task

import com.optimize.performance.launchstarter.task.Task
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.support.db.FastAndroidDB

/**
 * Created by Jerry on 2021/4/17.
 */
class DBInitTask:Task() {
    override fun run() {
        //初始化db
        NLog.d("task", "DBInitTask run --->")
        FastAndroidDB.setDB()
    }
}
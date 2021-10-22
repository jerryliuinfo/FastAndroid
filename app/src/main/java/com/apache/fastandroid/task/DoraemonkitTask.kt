package com.apache.fastandroid.task

import com.blankj.utilcode.util.Utils
import com.optimize.performance.launchstarter.task.Task
import com.tesla.framework.applike.FApplication
import com.tesla.framework.common.util.log.NLog

/**
 * Created by Jerry on 2021/4/17.
 */
class DoraemonkitTask: Task() {

    override fun run() {
        NLog.d("task", "DoraemonkitTask run --->")
        Utils.init(FApplication.getApplication())
    }
}
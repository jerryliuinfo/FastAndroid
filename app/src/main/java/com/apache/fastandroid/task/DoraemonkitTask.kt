package com.apache.fastandroid.task

import com.apache.fastandroid.app.FastAndroidApplication
import com.blankj.utilcode.util.Utils
import com.didichuxing.doraemonkit.DoraemonKit
import com.optimize.performance.launchstarter.task.Task
import com.tesla.framework.applike.FrameworkApplication
import com.tesla.framework.common.util.log.NLog

/**
 * Created by Jerry on 2021/4/17.
 */
class DoraemonkitTask: Task() {

    override fun run() {
        NLog.d("task", "DoraemonkitTask run --->")
        DoraemonKit.APPLICATION = FrameworkApplication.getApplication()
        Utils.init(FrameworkApplication.getApplication())
    }
}
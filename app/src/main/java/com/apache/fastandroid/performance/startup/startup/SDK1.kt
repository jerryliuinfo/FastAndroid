package com.apache.fastandroid.performance.startup.startup

import android.content.Context
import com.apache.fastandroid.performance.TaskUtil
import com.tesla.framework.component.startup.Startup

/**
 * Created by Jerry on 2022/1/3.
 */
class SDK1: Startup() {
    override fun create(context: Context) {
        TaskUtil.runTask1()
    }

    override fun callCreateOnMainThread(): Boolean {
        return true
    }

    override fun waitOnMainThread(): Boolean {
        return false
    }


}
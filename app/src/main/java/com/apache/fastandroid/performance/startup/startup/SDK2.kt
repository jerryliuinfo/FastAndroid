package com.apache.fastandroid.performance.startup.startup

import android.content.Context
import com.apache.fastandroid.performance.TaskUtil
import com.tesla.framework.component.startup.Startup

/**
 * Created by Jerry on 2022/1/3.
 */
class SDK2: Startup() {
    override fun create(context: Context) {
        TaskUtil.runTask2()
    }

    override fun callCreateOnMainThread(): Boolean {
        return false
    }

    override fun waitOnMainThread(): Boolean {
        return false
    }

    /*override fun dependencies(): List<Class<out Startup>>? {
        return mutableListOf(SDK1::class.java)
    }*/
}
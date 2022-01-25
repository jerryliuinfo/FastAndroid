package com.apache.fastandroid.crash

import android.app.Application
import com.apache.fastandroid.app.FastApplication
import com.tencent.bugly.crashreport.CrashReport

/**
 * Created by Jerry on 2022/1/25.
 */
object Fabric {

    @JvmStatic
    fun init(application: Application){
        CrashReport.initCrashReport(application.applicationContext, "a397701fd5", false)
    }
}
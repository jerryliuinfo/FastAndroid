package com.apache.fastandroid.jetpack.lifecycle.lifecycleservice

import android.content.Intent
import androidx.lifecycle.LifecycleService
import com.tesla.framework.common.util.log.NLog

/**
 * Created by Jerry on 2021/2/9.
 */
class MyService: LifecycleService() {

//    private var serviceObserver:ServiceObserver = ServiceObserver()

    companion object{
         const val TAG = "MyService"
    }

    init {
        lifecycle.addObserver(ServiceObserver())
    }

    override fun onCreate() {
        super.onCreate()
        NLog.d(TAG, "MyService onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        NLog.d(TAG, "MyService onStartCommand")

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        NLog.d(TAG, "MyService onDestroy")
    }
}
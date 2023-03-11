package com.apache.fastandroid.jetpack.lifecycle.service

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LifecycleService

/**
 * Created by Jerry on 2021/2/9.
 */
class MyService: LifecycleService() {

//    private var serviceObserver:ServiceObserver = ServiceObserver()

    companion object{
         const val TAG = "MyService"

        fun start(context: Context){
            context.startService(Intent(context,MyService::class.java))
        }

        fun stop(context: Context){
            context.stopService(Intent(context,MyService::class.java))
        }
    }

    init {
        lifecycle.addObserver(ServiceObserver())
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
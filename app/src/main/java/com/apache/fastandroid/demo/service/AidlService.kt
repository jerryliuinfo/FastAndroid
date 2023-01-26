package com.apache.fastandroid.demo.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.apache.fastandroid.IAppService
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.kt.showShortToast
import kotlin.concurrent.thread
import kotlin.random.Random

/**
 * Created by Jerry on 2023/1/1.
 */
class AidlService : Service() {

    private var data: String = "默认数据"

    private var running = false

    override fun onCreate() {
        super.onCreate()
        Logger.d("AidlService onCreate")
        running = true
        showShortToast("AidlService onCreate")

//        thread {
//            while (running) {
//                Logger.d("AidlService data:$data")
//                Thread.sleep(1000)
//            }
//        }.start()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Logger.d("AidlService onStartCommand")
        showShortToast("AidlService onStartCommand")

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.d("AidlService onDestroy")
        showShortToast("AidlService onDestroy")

        running = false

    }

    private val mBinder = object : IAppService.Stub() {
        override fun basicTypes(
            anInt: Int,
            aLong: Long,
            aBoolean: Boolean,
            aFloat: Float,
            aDouble: Double,
            aString: String?
        ) {
        }

        override fun setData(data: String?) {
            showShortToast("AidlService setData")

            this@AidlService.data = data ?: "我是默认值"
        }

    }

    override fun onBind(intent: Intent?): IBinder?{
        return mBinder
    }


}
package com.tesla.framework.component.countdown

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.kt.runOnUiThread
import java.util.Timer
import java.util.TimerTask

/**
 * Created by Jerry on 2023/3/5.
 */
class LifeCycleCountDownByTimer(
    count: Long = 3000, delay:Long = 0, period: Long = 1000,

    lifecycleOwner: LifecycleOwner,
    listener:ICountDownListener?= null):
    CountDownByTimer(count, delay, period, listener),DefaultLifecycleObserver {
    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        Logger.d("LifeCycleCountDownByTimer onDestroy")
        stop()
    }
}
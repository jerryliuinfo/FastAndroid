package com.tesla.framework.component.countdown

import com.tesla.framework.kt.runOnUiThread
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

/**
 * Created by Jerry on 2023/3/5.
 */
open class CountDownByCoroutine(
    private var totalCount: Long, private val period: Long,
    private val listener: ICountDownListener
) : ICountDownAction {

    private var mJob: Job? = null


    override fun start(): ICountDownAction {
        mJob?.cancel()
        var remainSeconds = totalCount
        mJob = GlobalScope.launch {
            while (remainSeconds > 0) {
                listener.onTick(remainSeconds)
                delay(period)
                remainSeconds--
            }
            listener.onFinish()
        }
        return this
    }

    override fun stop() {
       cancel()
    }

    override fun cancel() {
        mJob?.cancel()
    }


}
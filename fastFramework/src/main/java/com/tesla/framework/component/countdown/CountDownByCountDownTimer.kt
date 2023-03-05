package com.tesla.framework.component.countdown

import android.os.CountDownTimer

/**
 * Created by Jerry on 2023/3/5.
 */
class CountDownByCountDownTimer(private val millsInFuture:Long, private val interval:Long,private val listener:ICountDownListener):ICountDownAction {

    var onTick: ((millisUntilFinished: Long) -> Unit)? = null
    var onFinish: (() -> Unit)? = null

    private var mTimer: CountDownTimer ?= null

    override fun start():ICountDownAction {
        cancel()
        mTimer = object :CountDownTimer(millsInFuture,interval){
            override fun onTick(millisUntilFinished: Long) {
                onTick?.invoke(millisUntilFinished)

                listener.onTick(millisUntilFinished)
            }

            override fun onFinish() {
                stop()
                onFinish?.invoke()
                listener.onFinish()
            }

        }.start()
        return this

    }

    override fun stop() {
       cancel()

    }

    override fun cancel() {
        mTimer?.cancel()
        mTimer = null
    }
}
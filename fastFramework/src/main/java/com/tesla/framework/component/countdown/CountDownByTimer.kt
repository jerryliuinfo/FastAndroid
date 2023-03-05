package com.tesla.framework.component.countdown

import com.tesla.framework.kt.runOnUiThread
import java.util.Timer
import java.util.TimerTask

/**
 * Created by Jerry on 2023/3/5.
 */
open class CountDownByTimer(private var count: Long, private val delay:Long, private val period: Long,
                       private val listener:ICountDownListener?= null):ICountDownAction {

    private var mTimer: Timer? = null


    private inner class CountDownTask : TimerTask() {
        override fun run() {
            if (count > 0){
                runOnUiThread {
                    listener?.onTick(count)
                }
                count --
            }else{
                runOnUiThread {
                    listener?.onTick(count)
                }
                stop()
            }
        }

    }

    override fun start():ICountDownAction {
        cancel()
        mTimer = Timer().apply {
            schedule(CountDownTask(), 0, period)
        }
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
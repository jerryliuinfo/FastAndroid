package com.tesla.framework.component.countdown

/**
 * Created by Jerry on 2023/3/5.
 */
interface ICountDownListener {

    fun onTick(count:Long)

    fun onFinish()
}
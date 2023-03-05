package com.tesla.framework.component.countdown

/**
 * Created by Jerry on 2023/3/5.
 */
interface ICountDownAction {

    fun start():ICountDownAction

    fun stop()

    fun cancel()
}
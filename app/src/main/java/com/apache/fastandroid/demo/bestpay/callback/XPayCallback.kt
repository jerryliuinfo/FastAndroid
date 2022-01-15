package com.apache.fastandroid.demo.bestpay.callback

/**
 * Created by Jerry on 2022/1/15.
 */
interface  XPayCallback {
    fun success()

    fun failed(code:Int, message:String?)

    fun cancel()
}
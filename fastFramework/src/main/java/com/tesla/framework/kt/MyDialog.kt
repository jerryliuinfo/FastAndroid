package com.tesla.framework.kt

import android.content.Context

/**
 * Created by Jerry on 2022/7/16.
 */
class MyDialog(private val context: Context) {

    inline fun show(func:MyDialog.() -> Unit):MyDialog = apply {
        this.func()
        this.realShow()
    }

    fun realShow(){
        println("real show dialog")
    }

    fun message(msg:String):MyDialog = apply {
        showShortToast("set message to ${msg}")

    }
}
package com.apache.fastandroid.util.extensitons

import com.apache.fastandroid.demo.component.loadsir.callback.EmptyCallback
import com.apache.fastandroid.demo.component.loadsir.callback.LoadingCallback
import com.blankj.utilcode.util.ThreadUtils
import com.kingja.loadsir.core.LoadService

/**
 * Created by Jerry on 2021/12/16.
 */
fun LoadService<Any>.showLoading(){
    showCallback(LoadingCallback::class.java)
}

fun LoadService<Any>.showEmpty(){
    showCallback(EmptyCallback::class.java)
}

fun runOnUIDelay(block: () -> Unit, delay:Long){
    ThreadUtils.runOnUiThreadDelayed(block,delay)
}

fun runOnUI(block: () -> Unit){
    ThreadUtils.runOnUiThread { block }
}
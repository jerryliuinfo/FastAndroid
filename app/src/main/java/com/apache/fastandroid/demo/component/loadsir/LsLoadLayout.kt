package com.apache.fastandroid.demo.component.loadsir

import android.content.Context
import android.widget.FrameLayout

/**
 * Created by Jerry on 2023/4/16.
 */
class LsLoadLayout(context: Context) : FrameLayout(context) {

    private val mCallbacks:Map<Class<out LsCallback>, LsCallback> = hashMapOf()

    fun addCallback(callback: LsCallback){
        if (!mCallbacks.containsKey(callback.javaClass)){
            mCallbacks.plus(Pair(callback.javaClass, callback))
        }
    }
}
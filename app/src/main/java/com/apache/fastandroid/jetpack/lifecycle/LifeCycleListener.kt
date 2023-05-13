package com.apache.fastandroid.jetpack.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * Created by Jerry on 2021/4/28.
 */
class LifeCycleListener : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        println("LifeCycleListener onCreate")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        println("LifeCycleListener onDestroy")
    }

    fun addLifecycleObserver(lifecycleOwner: LifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(this)
    }

}
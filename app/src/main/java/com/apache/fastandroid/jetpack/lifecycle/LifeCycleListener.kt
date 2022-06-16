package com.apache.fastandroid.jetpack.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.tesla.framework.common.util.log.NLog
import com.apache.fastandroid.jetpack.lifecycle.LifeCycleListener

/**
 * Created by Jerry on 2021/4/28.
 */
class LifeCycleListener : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        NLog.d(TAG, "LifeCycleListener onCreate")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        NLog.d(TAG, "LifeCycleListener onDestroy")
    }

    fun addLifecycleObserver(lifecycleOwner: LifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    companion object {
        val TAG = LifeCycleListener::class.java.simpleName
    }
}
package com.apache.fastandroid.jetpack.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.apache.fastandroid.LogUtils
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.component.lifecycle.FullLifecycleObserver
import com.tesla.framework.component.lifecycle.FullLifecycleObserverAdapter
import com.tesla.framework.component.lifecycle.SimpleLifeCycleObserver

/**
 * Created by Jerry on 2020/11/1.
 */
class LifeCycleListener(lifecycleOwner: LifecycleOwner): SimpleLifeCycleObserver() {

    companion object{
        const val TAG = "LifeCycleListener"
    }
    init {
        lifecycleOwner.lifecycle.addObserver(FullLifecycleObserverAdapter(lifecycleOwner,this))
    }

    override fun onCreate(owner: LifecycleOwner) {
        NLog.d(TAG, "LifeCycleListener onCreate")
    }


    override fun onDestroy(owner: LifecycleOwner) {
        NLog.d(TAG, "LifeCycleListener onDestroy")
    }

}
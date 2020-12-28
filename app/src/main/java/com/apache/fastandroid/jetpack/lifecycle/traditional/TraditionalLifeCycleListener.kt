package com.apache.fastandroid.jetpack.lifecycle.traditional

import androidx.lifecycle.LifecycleOwner
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.component.lifecycle.FullLifecycleObserverAdapter
import com.tesla.framework.component.lifecycle.SimpleLifeCycleObserver

/**
 * Created by Jerry on 2020/11/1.
 */
class TraditionalLifeCycleListener {

    companion object{
        const val TAG = "TraditionalLifeCycleListener"
    }

    fun onCreate(owner: LifecycleOwner) {
        NLog.d(TAG, "TraditionalLifeCycleListener onCreate")
    }

    fun onDestroy(owner: LifecycleOwner) {
        NLog.d(TAG, "TraditionalLifeCycleListener onDestroy")
    }

}
package com.apache.fastandroid.jetpack.lifecycle

import androidx.lifecycle.LifecycleOwner
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.component.lifecycle.FullLifecycleObserverAdapter
import com.tesla.framework.component.lifecycle.SimpleLifeCycleObserver

/**
 * Created by Jerry on 2020/11/1.
 */
class JetPackLifeCycleListener(lifecycleOwner: LifecycleOwner): SimpleLifeCycleObserver() {

    companion object{
        const val TAG = "TraditionalLifeCycleListener"
    }
    init {
        lifecycleOwner.lifecycle.addObserver(FullLifecycleObserverAdapter(lifecycleOwner,this))
    }

    override fun onCreate(owner: LifecycleOwner) {
        NLog.d(TAG, "TraditionalLifeCycleListener onCreate")
    }


    override fun onDestroy(owner: LifecycleOwner) {
        NLog.d(TAG, "TraditionalLifeCycleListener onDestroy")
    }

}
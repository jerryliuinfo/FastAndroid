package com.apache.fastandroid.jetpack.lifecycle

import androidx.lifecycle.LifecycleOwner
import com.tesla.framework.common.util.log.FastLog
import com.tesla.framework.component.lifecycle.FullLifecycleObserverAdapter
import com.tesla.framework.component.lifecycle.SimpleLifeCycleObserver

/**
 * Created by Jerry on 2020/11/1.
 */
class JetPackLifeCycleListener(lifecycleOwner: LifecycleOwner): SimpleLifeCycleObserver() {

    companion object{
        const val TAG = "JetPackLifeCycleListener"
    }
    init {
        lifecycleOwner.lifecycle.addObserver(FullLifecycleObserverAdapter(lifecycleOwner,this))
    }

    override fun onCreate(owner: LifecycleOwner) {
        FastLog.d(TAG, "JetPackLifeCycleListener onCreate")
    }


    override fun onDestroy(owner: LifecycleOwner) {
        FastLog.d(TAG, "JetPackLifeCycleListener onDestroy")
    }

}
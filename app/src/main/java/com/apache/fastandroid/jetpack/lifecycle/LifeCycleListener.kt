package com.apache.fastandroid.jetpack.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.apache.fastandroid.LogUtils
import com.tesla.framework.applike.FrameworkApplication.onDestroy

/**
 * Created by Jerry on 2020/11/1.
 */
class LifeCycleListener:ILifeCycle,LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    override fun onCreate() {
        LogUtils.d("onCreate   ---->")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    override fun onStart() {
        LogUtils.d("onStart   ---->")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun onResume() {
        LogUtils.d("onResume   ---->")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    override fun onPause() {
        LogUtils.d("onPause   ---->")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun onStop() {
        LogUtils.d("onStop   ---->")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onDestroy() {
        LogUtils.d("onDestroy   ---->")
    }
}
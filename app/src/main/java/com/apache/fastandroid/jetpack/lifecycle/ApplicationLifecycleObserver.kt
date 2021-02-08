package com.apache.fastandroid.jetpack.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.blankj.utilcode.util.ToastUtils
import com.tesla.framework.common.util.log.NLog

/**
 * Created by Jerry on 2021/2/8.
 */
class ApplicationLifecycleObserver:LifecycleObserver {
    companion object{
        private const val TAG = "ApplicationLifecycleObserver"
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForground() {
        NLog.d(TAG, "onAppForground --->")
        ToastUtils.showShort("App已进入前台")

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackground() {
        NLog.d(TAG, "onAppBackground --->")
        ToastUtils.showShort("App已进入后台")
    }

}
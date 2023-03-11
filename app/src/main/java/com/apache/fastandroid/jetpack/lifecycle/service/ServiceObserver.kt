package com.apache.fastandroid.jetpack.lifecycle.service

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.tesla.framework.component.logger.Logger

/**
 * Created by Jerry on 2021/2/9.
 */
class ServiceObserver: LifecycleObserver {

    init {

    }
    /*override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        NLog.d(MyService.TAG, "ServiceObserver start get location")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        NLog.d(MyService.TAG, "ServiceObserver onDestroy stop location")
    }*/

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun startLocation(){
        Logger.d("ServiceObserverNew startLocation  -->")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun stopLocation() {
        Logger.d("ServiceObserverNew stopLocation  -->")
    }
}
package com.apache.fastandroid.jetpack.lifecycle.lifecycleservice

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.apache.fastandroid.jetpack.lifecycle.LocationListener
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.component.lifecycle.FullLifecycleObserverAdapter
import com.tesla.framework.component.lifecycle.SimpleLifeCycleObserver

/**
 * Created by Jerry on 2021/2/9.
 */
class ServiceObserver: LifecycleObserver {

    init {

        NLog.d(MyService.TAG, "ServiceObserver init")
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
        NLog.d(MyService.TAG, "ServiceObserverNew startLocation  -->")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun stopLocation() {
        NLog.d(MyService.TAG, "ServiceObserverNew stopLocation  -->")
    }
}
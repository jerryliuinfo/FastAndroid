package com.tesla.framework.component.lifecycle

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner




/**
 * Created by Jerry on 2020/12/10.
 */
interface FullLifecycleObserverNew:LifecycleObserver {

    fun onCreate(owner: LifecycleOwner)

    fun onStart(owner: LifecycleOwner)

    fun onResume(owner: LifecycleOwner)

    fun onPause(owner: LifecycleOwner)

    fun onStop(owner: LifecycleOwner)

    fun onDestroy(owner: LifecycleOwner)
}
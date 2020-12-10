package com.tesla.framework.component.lifecycle

import androidx.lifecycle.LifecycleOwner

/**
 * Created by Jerry on 2020/12/10.
 */
open class SimpleLifeCycleObserver:FullLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
    }

    override fun onStart(owner: LifecycleOwner) {
    }

    override fun onResume(owner: LifecycleOwner) {
    }

    override fun onPause(owner: LifecycleOwner) {
    }

    override fun onStop(owner: LifecycleOwner) {
    }

    override fun onDestroy(owner: LifecycleOwner) {
    }
}
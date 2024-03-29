package com.tesla.framework.component.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent




/**
 * Created by Jerry on 2020/12/10.
 */
class LifecycleObserverAdapter(private val mLifecycleOwner: LifecycleOwner, private val mObserver: DefaultLifecycleObserver):LifecycleObserver {


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        //将事件回调给监听者
        mObserver.onCreate(mLifecycleOwner)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        mObserver.onStart(mLifecycleOwner)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        mObserver.onResume(mLifecycleOwner)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        mObserver.onPause(mLifecycleOwner)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        mObserver.onStop(mLifecycleOwner)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        mObserver.onDestroy(mLifecycleOwner)
    }



}
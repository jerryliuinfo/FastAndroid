package com.apache.fastandroid.jetpack.lifecycle;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import com.apache.fastandroid.util.MainLogUtil;

/**
 * Created by Jerry on 2020-05-16.
 */
public class MyLifeCycleObserver implements LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate2(){
        MainLogUtil.d("MyLifeCycleObserver onCreate");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume2(){
        MainLogUtil.d("MyLifeCycleObserver onResume");

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause2(){
        MainLogUtil.d("MyLifeCycleObserver onPause");

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop2(){
        MainLogUtil.d("MyLifeCycleObserver onStop");

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy2(){
        MainLogUtil.d("MyLifeCycleObserver onDestroy");

    }
}

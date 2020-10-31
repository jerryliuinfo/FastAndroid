package com.apache.fastandroid.jetpack.lifecycle;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

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
        MainLogUtil.d("MyLifeCycleObserver onResumeAction");

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause2(){
        MainLogUtil.d("MyLifeCycleObserver onPauseAction");

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

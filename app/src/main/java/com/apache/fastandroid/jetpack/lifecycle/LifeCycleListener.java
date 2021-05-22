package com.apache.fastandroid.jetpack.lifecycle;

import com.tesla.framework.common.util.log.NLog;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * Created by Jerry on 2021/4/28.
 */
public class LifeCycleListener implements DefaultLifecycleObserver {
    public static final String TAG = LifeCycleListener.class.getSimpleName();

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        NLog.d(TAG, "LifeCycleListener onCreate");
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        NLog.d(TAG, "LifeCycleListener onDestroy");
    }

    /*@Override
    public void onCreate(@NotNull LifecycleOwner owner) {
        super.onCreate(owner);
        NLog.d(TAG, "LifeCycleListener onCreate");
    }

    @Override
    public void onDestroy(@NotNull LifecycleOwner owner) {
        super.onDestroy(owner);
        NLog.d(TAG, "LifeCycleListener onDestroy");

    }*/


    public void addLifecycleObserver(LifecycleOwner lifecycleOwner){
//        lifecycleOwner.getLifecycle().addObserver(new LifecycleObserverAdapter(lifecycleOwner,this));
        lifecycleOwner.getLifecycle().addObserver(this);
    }
}

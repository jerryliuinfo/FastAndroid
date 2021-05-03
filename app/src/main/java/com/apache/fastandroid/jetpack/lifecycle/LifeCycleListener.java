package com.apache.fastandroid.jetpack.lifecycle;

import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.component.lifecycle.LifecycleObserverAdapter;
import com.tesla.framework.component.lifecycle.SimpleLifeCycleObserver;

import org.jetbrains.annotations.NotNull;

import androidx.lifecycle.LifecycleOwner;

/**
 * Created by Jerry on 2021/4/28.
 */
public class LifeCycleListener extends SimpleLifeCycleObserver {
    public static final String TAG = LifeCycleListener.class.getSimpleName();



    @Override
    public void onCreate(@NotNull LifecycleOwner owner) {
        super.onCreate(owner);
        NLog.d(TAG, "LifeCycleListener onCreate");
    }

    @Override
    public void onDestroy(@NotNull LifecycleOwner owner) {
        super.onDestroy(owner);
        NLog.d(TAG, "LifeCycleListener onDestroy");

    }


    public void addLifecycleObserver(LifecycleOwner lifecycleOwner){
        lifecycleOwner.getLifecycle().addObserver(new LifecycleObserverAdapter(lifecycleOwner,this));
    }
}

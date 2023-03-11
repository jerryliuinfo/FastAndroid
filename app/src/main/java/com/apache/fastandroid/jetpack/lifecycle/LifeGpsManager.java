package com.apache.fastandroid.jetpack.lifecycle;

import android.os.Handler;
import android.os.Message;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * Created by Jerry on 2021/8/19.
 */
public class LifeGpsManager implements DefaultLifecycleObserver {
    public static final String TAG = LifeGpsManager.class.getSimpleName();
    private LifeGpsManager(){}
    private static class SingletonHolder{
        private SingletonHolder(){

        }
        private final static LifeGpsManager INSTANCE = new LifeGpsManager();
    }

    public static LifeGpsManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private boolean isActive;


    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        setActive(owner.getLifecycle());
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        setActive(owner.getLifecycle());
        calculte();
    }

    public void setActive(Lifecycle lifecycle) {
        isActive = lifecycle.getCurrentState().isAtLeast(Lifecycle.State.STARTED);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };

    public void calculte(){
        Observable.interval(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                    }
                });
    }
}

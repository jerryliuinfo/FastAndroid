package com.tesla.framework.component.lifecycle;

import android.os.Handler;
import android.os.Looper;

import com.tesla.framework.common.util.Preconditions;
import com.tesla.framework.common.util.log.FastLog;

import org.jetbrains.annotations.NotNull;

import androidx.lifecycle.LifecycleOwner;


public class LifecycleHandler extends Handler  {

    public static final String TAG = LifecycleHandler.class.getSimpleName();

    private LifecycleOwner lifecycleOwner;

    public LifecycleHandler(final LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
        addObserver();
    }

    public LifecycleHandler(final Callback callback, final LifecycleOwner lifecycleOwner) {
        super(callback);
        this.lifecycleOwner = lifecycleOwner;
        addObserver();
    }

    public LifecycleHandler(final Looper looper, final LifecycleOwner lifecycleOwner) {
        super(looper);
        this.lifecycleOwner = lifecycleOwner;
        addObserver();
    }

    public LifecycleHandler(final Looper looper, final Callback callback, final LifecycleOwner lifecycleOwner) {
        super(looper, callback);
        this.lifecycleOwner = lifecycleOwner;
        addObserver();
    }

    private void addObserver() {
        Preconditions.checkNotNull(lifecycleOwner);
        if (lifecycleOwner != null){
            lifecycleOwner.getLifecycle().addObserver(new FullLifecycleObserverAdapter(lifecycleOwner,observer));
        }
    }

    private FullLifecycleObserver observer = new SimpleLifeCycleObserver(){
        @Override
        public void onCreate(@NotNull LifecycleOwner owner) {
            super.onCreate(owner);
            FastLog.d(TAG, "LifecycleHandler onCreate");
        }

        @Override
        public void onDestroy(@NotNull LifecycleOwner owner) {
            super.onDestroy(owner);

            FastLog.d(TAG, "LifecycleHandler onDestroy");
            removeCallbacksAndMessages(null);
        }
    };


}

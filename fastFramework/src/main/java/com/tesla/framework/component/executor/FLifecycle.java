package com.tesla.framework.component.executor;

import android.arch.lifecycle.LifecycleObserver;
import android.support.annotation.NonNull;

/**
 * Created by Jerry on 2020-05-16.
 */
public abstract class FLifecycle {

    public abstract void addObserver(@NonNull LifecycleObserver observer);

    public abstract void removeObserver(@NonNull LifecycleObserver observer);


    @NonNull
    public abstract android.arch.lifecycle.Lifecycle.State getCurrentState();

    @SuppressWarnings("WeakerAccess")
    public enum Event {

        ON_CREATE,

        ON_START,

        ON_RESUME,

        ON_PAUSE,

        ON_STOP,

        ON_DESTROY,

        ON_ANY
    }


    @SuppressWarnings("WeakerAccess")
    public enum State {

        DESTROYED,


        INITIALIZED,


        CREATED,


        STARTED,


        RESUMED;


        public boolean isAtLeast(@NonNull FLifecycle.State state) {
            return compareTo(state) >= 0;
        }
    }
}

package com.tesla.framework.component.executor;

import androidx.lifecycle.LifecycleOwner;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.tesla.framework.common.util.FrameworkLogUtil;
import com.tesla.framework.support.thread.ThreadUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jerry on 2020-05-16.
 */
public abstract class FLiveData<T> {

    private final Object mDataLock = new Object();
    private static final Object NOT_SET = new Object();
    private volatile Object mPendingData = NOT_SET;
    private volatile Object mData = NOT_SET;

    static final int START_VERSION = -1;
    private int mVersion = START_VERSION;
    private int mActiveCount = 0;


    private boolean mDispatchingValue;
    @SuppressWarnings("FieldCanBeLocal")
    private boolean mDispatchInvalidated;
    private Map<Observer<T>, ObserverWrapper> mObservers =
            new HashMap<>();


    protected void postValue(T value) {
        FrameworkLogUtil.d("postValue value begin %s, thread:%s, try get lock", value,Thread.currentThread().getName());
        long startTime = System.currentTimeMillis();

        boolean postTask;
        synchronized (mDataLock) {
            postTask = mPendingData == NOT_SET;
            mPendingData = value;
        }
        FrameworkLogUtil.d("postValue value: %s get lock success, cost time: %s ms, postTask: %s",value,(System.currentTimeMillis() - startTime),postTask);
        if (!postTask) {
            FrameworkLogUtil.d("postValue value: %s postTask: false, return ",value);
            return;
        }
        FrameworkLogUtil.d("postValue value postToMainThread %s", value);
        FArchTaskExecutor.getInstance().postToMainThread(mPostValueRunnable);

    }

    private final Runnable mPostValueRunnable = new Runnable() {
        @Override
        public void run() {
            FrameworkLogUtil.d("mPostValueRunnable mPendingData: %s, thread:%s", JSON.toJSON(mPendingData),Thread.currentThread().getName());
            Object newValue;
            synchronized (mDataLock) {
                newValue = mPendingData;
                FrameworkLogUtil.d("mPostValueRunnable mPendingData: %s, reset mPendingData = NOT_SET", mPendingData,Thread.currentThread().getName());

                mPendingData = NOT_SET;
            }
            //noinspection unchecked
            setValue((T) newValue);
        }
    };

    /**
     * setValue 要求在主线程中执行
     * @param value
     */
    protected void setValue(T value) {
       // assertMainThread("setValue");
        mVersion++;
        mData = value;
        dispatchingValue(null);
    }


    private void dispatchingValue(ObserverWrapper initiator) {
        if (mDispatchingValue) {
            mDispatchInvalidated = true;
            return;
        }
        mDispatchingValue = true;
        do {
            mDispatchInvalidated = false;
            //生命周期改变调用的方法 initiator 不为 null
            if (initiator != null) {
                considerNotify(initiator);
                initiator = null;
            } else {
                    ////postValue/setValue 方法调用 传递的 initiator 为 null
//                for (Iterator<Map.Entry<Observer<T>, ObserverWrapper>> iterator =  mObservers.iteratorWithAdditions(); iterator.hasNext(); ) {
//                    considerNotify(iterator.next().getValue());
//                    if (mDispatchInvalidated) {
//                        break;
//                    }
//                }
            }

            if (mDispatchInvalidated){
                break;
            }
        } while (mDispatchInvalidated);
        mDispatchingValue = false;
    }

    private void considerNotify(ObserverWrapper observer) {
        ////检查状态 确保不会分发给 inactive 的 observer
        if (!observer.mActive) {
            return;
        }

        if (!observer.shouldBeActive()) {
            observer.activeStateChanged(false);
            return;
        }
        //setValue 会增加 version ,初始 version 为-1
        if (observer.mLastVersion >= mVersion) {
            return;
        }
        observer.mLastVersion = mVersion;
        //noinspection unchecked
        observer.mObserver.onChanged((T) mData);
    }

    private static void assertMainThread(String methodName) {
        if (!ThreadUtils.isMainThread()) {
            throw new IllegalStateException("Cannot invoke " + methodName + " on a background"
                    + " thread");
        }
    }


    /**
     * Returns the current value.
     * Note that calling this method on a background thread does not guarantee that the latest
     * value set will be received.
     *
     * @return the current value
     */
    @Nullable
    public T getValue() {
        Object data = mData;
        if (data != NOT_SET) {
            //noinspection unchecked
            return (T) data;
        }
        return null;
    }

    int getVersion() {
        return mVersion;
    }

    /**
     * Called when the number of active observers change to 1 from 0.
     * <p>
     * This callback can be used to know that this LiveData is being used thus should be kept
     * up to date.
     */
    protected void onActive() {

    }


    protected void onInactive() {

    }


    private abstract class ObserverWrapper {
        final Observer<T> mObserver;
        boolean mActive;
        int mLastVersion = START_VERSION;

        ObserverWrapper(Observer<T> observer) {
            mObserver = observer;
        }

        abstract boolean shouldBeActive();

        boolean isAttachedTo(LifecycleOwner owner) {
            return false;
        }

        void detachObserver() {
        }

        void activeStateChanged(boolean newActive) {
            if (newActive == mActive) {
                return;
            }
            // immediately set active state, so we'd never dispatch anything to inactive
            // owner
            mActive = newActive;
            boolean wasInactive = FLiveData.this.mActiveCount == 0;
            FLiveData.this.mActiveCount += mActive ? 1 : -1;
            if (wasInactive && mActive) {
                onActive();
            }
            if (FLiveData.this.mActiveCount == 0 && !mActive) {
                onInactive();
            }
            if (mActive) {
                dispatchingValue(this);
            }
        }
    }

}

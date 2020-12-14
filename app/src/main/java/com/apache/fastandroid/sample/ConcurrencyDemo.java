package com.apache.fastandroid.sample;

import com.blankj.utilcode.util.ThreadUtils;

/**
 * Created by Jerry on 2020-05-16.
 */
public class ConcurrencyDemo<T> {
    private final Object mDataLock = new Object();
    private static final Object NOT_SET = new Object();
    private volatile Object mPendingData = NOT_SET;
    private volatile Object mData = NOT_SET;

    static final int START_VERSION = -1;
    private int mVersion = START_VERSION;

    private boolean mDispatchingValue;
    @SuppressWarnings("FieldCanBeLocal")
    private boolean mDispatchInvalidated;


    protected void postValue(T value) {
        boolean postTask;
        synchronized (mDataLock) {
            postTask = mPendingData == NOT_SET;
            mPendingData = value;
        }
        if (!postTask) {
            return;
        }

    }

    private final Runnable mPostValueRunnable = new Runnable() {
        @Override
        public void run() {
            Object newValue;
            synchronized (mDataLock) {
                newValue = mPendingData;
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
        assertMainThread("setValue");
        mVersion++;
        mData = value;
       dispatchingValue();
    }


    private void dispatchingValue() {
        if (mDispatchingValue) {
            mDispatchInvalidated = true;
            return;
        }
        mDispatchingValue = true;
        do {
            mDispatchInvalidated = false;
//            if (initiator != null) {
//                considerNotify(initiator);
//                initiator = null;
//            } else {
//                for (Iterator<Map.Entry<Observer<T>, LiveData.ObserverWrapper>> iterator =
//                     mObservers.iteratorWithAdditions(); iterator.hasNext(); ) {
//                    considerNotify(iterator.next().getValue());
//                    if (mDispatchInvalidated) {
//                        break;
//                    }
//                }
//            }

            if (mDispatchInvalidated){
                break;
            }
        } while (mDispatchInvalidated);
        mDispatchingValue = false;
    }

    private static void assertMainThread(String methodName) {
        if (!ThreadUtils.isMainThread()) {
            throw new IllegalStateException("Cannot invoke " + methodName + " on a background"
                    + " thread");
        }
    }


}

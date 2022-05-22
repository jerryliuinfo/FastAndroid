package com.knightboot.spwaitkiller;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import java.util.LinkedList;

import androidx.annotation.NonNull;

/**
 * created by Knight-ZXW on 2021/9/14
 */
class ProxySWork<T> extends LinkedList<T> {
    private final LinkedList<T> proxy;

    private final Handler sHandler;

    private final QueueWorkAspect queueWorkAspect;

    private static final int VERSION_CODES_S =31;

    public ProxySWork(LinkedList<T> proxy,
                      Looper looper, QueueWorkAspect queueWorkAspect) {
        this.proxy = proxy;
        sHandler = new Handler(looper);
        this.queueWorkAspect = queueWorkAspect;
    }

    // is thread safe
    @NonNull
    @Override
    public Object clone() {
        // <=31
        if (Build.VERSION.SDK_INT<VERSION_CODES_S){
            delegateWork();
            return new LinkedList<T>();
        }else {
            return proxy.clone();
        }
    }

    private void delegateWork() {
        if (proxy.size()==0){
            return;
        }
        LinkedList<Runnable> works = (LinkedList<Runnable>)  proxy.clone();
        proxy.clear();
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                for (Runnable w : works) {
                    w.run();
                }
            }
        });
    }

    @Override
    public boolean add(T t) {
        return proxy.add(t);
    }

    @Override
    public int size() {
        //Android 12 change:
        if (Build.VERSION.SDK_INT>=VERSION_CODES_S){
            delegateWork();
            this.queueWorkAspect.processPendingWorkDone();
            return 0;
        }else {
            return proxy.size();
        }
    }

    // is thread safe
    @Override
    public void clear() {
        proxy.clear();
    }

    @Override
    public boolean isEmpty() {
        return proxy.isEmpty();
    }


    interface QueueWorkAspect {

        public void processPendingWorkDone();

    }
}


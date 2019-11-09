package com.tesla.framework.component.handler;

/**
 * author: 01370340
 * data: 2019-10-18
 * description:
 */
public class FLooper {

    private static ThreadLocal<FLooper> sThreadLocal = new ThreadLocal<>();

    public FMessageQueue mQueue;

    private Thread mThread;

    public FLooper() {
        mQueue = new FMessageQueue();
        mThread = Thread.currentThread();
    }

    public static void prepare(){
        if (sThreadLocal.get() != null){
            throw new RuntimeException("Looper.prepare can only call once");
        }
        sThreadLocal.set(new FLooper());
    }

    public static FLooper myLooper(){
        return sThreadLocal.get();
    }

    public static void loop(){
        FLooper me = myLooper();
        if (me == null){
            throw new RuntimeException("no Looper, Looper.prepare() wasn't called on this thread.");
        }
        final FMessageQueue queue = me.mQueue;

        for (;;){
            FMessage msg = queue.next();
            if (msg == null){
                continue;
            }
            //把消息转发给Handler
            msg.target.dispatchMessage(msg);
        }
    }
}

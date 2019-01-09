package com.tesla.framework.common.util.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by 01370340 on 2018/3/21.
 */

public class HandlerUtil {
    private static Handler sUiHandler = new Handler(Looper.getMainLooper());

    public static Handler getUIHandler(){
        return sUiHandler;
    }
    /**
     * 在UI线程运行
     *
     * @param runnable
     */
    public static void runUITask(Runnable runnable) {
        runUITask(runnable,0);
    }

    /**
     * 在UI线程延时运行
     *
     * @param runnable
     */
    public static void runUITask(Runnable runnable, long delayTime) {
        sUiHandler.postDelayed(runnable, delayTime);
    }

    public static void removeTask(Runnable r) {
        if (r == null) {
            sUiHandler.removeCallbacksAndMessages(null);
        } else {
            sUiHandler.removeCallbacks(r);
        }
    }

    public static class MyHandler extends Handler{
        private WeakReference<MessageReceiveListener> mWeakReference;

        public MyHandler(MessageReceiveListener listener) {
            this.mWeakReference = new WeakReference<>(listener);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            MessageReceiveListener listener = mWeakReference.get();
            if (listener != null && !listener.isActivityDestroyed()){
                listener.handleMessage(msg);
            }
        }
    }


    public interface MessageReceiveListener {
        boolean isActivityDestroyed();
        void handleMessage(Message message);
    }



}

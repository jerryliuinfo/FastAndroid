package com.apache.fastandroid.sample.messagequeue;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 */
public class MessageQueueThreadHandler extends Handler {
    private final QueueThreadExceptionHandler mExceptionHandler;

    public MessageQueueThreadHandler(Looper looper, QueueThreadExceptionHandler exceptionHandler) {
        super(looper);
        this.mExceptionHandler = exceptionHandler;
    }

    public void dispatchMessage(Message msg) {
        try {
            super.dispatchMessage(msg);
        } catch (Exception e) {
            this.mExceptionHandler.handleException(e);
        }
    }
}

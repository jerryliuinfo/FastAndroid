package com.tesla.framework.component.handler;

/**
 * author: 01370340
 * data: 2019-10-18
 * description:
 */
public class FHandler {
    private FLooper mFLooper;
    private FMessageQueue mQueue;

    public FHandler() {
        mFLooper = FLooper.myLooper();
        this.mQueue = mFLooper.mQueue;
    }

    //入队
    public void sendMessage(FMessage msg){
        msg.target = this;
        mQueue.enqueueMessage(msg);
    }

    //出队
    public void dispatchMessage(FMessage msg){
        handleMessage(msg);
    }

    public void handleMessage(FMessage msg){

    }



}

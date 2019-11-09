package com.tesla.framework.component.handler;

import com.tesla.framework.common.util.FrameworkLogUtil;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * author: 01370340
 * data: 2019-10-18
 * description:
 */
public class FMessageQueue {

    private FMessage[] mMessages;

    private int count;

    private int putIndex;

    private int takeIndex;

    private Condition notEmpty;

    private Condition notFull;


    private Lock mLock;



    public FMessageQueue() {
        mMessages = new FMessage[50];

        mLock = new ReentrantLock();

        notEmpty = mLock.newCondition();
        notFull = mLock.newCondition();


    }
    public static final String TAG = FMessageQueue.class.getSimpleName();

    public void enqueueMessage(FMessage msg){
        try {
            mLock.lock();
            //队列已经满了，子线程停止发送，阻塞
            while (count >= mMessages.length){
                try {
                    //调用condition.await()方法后会使得当前获取lock的线程进入到等待队列
                    FrameworkLogUtil.d("enqueueMessage count = %s,await ------->",count);
                    notFull.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            FrameworkLogUtil.d("enqueueMessage  count = %s, thread = %s, push message = %s",count,Thread.currentThread().getName(),msg);

            mMessages[putIndex] = msg;
            putIndex = (++putIndex >= mMessages.length? 0:putIndex);
            count++;
            //FrameworkLogUtil.d("enqueueMessage put msg putIndex = %s,");

            //有新的message对象，通知主线程
            notEmpty.signalAll();
        }finally {
            mLock.unlock();
        }


    }

    public FMessage next(){
        FMessage msg;
        try {
            mLock.lock();
            //FrameworkLogUtil.d( "next count = %s--->",count);
            //队列已经空了，通知主线程不要取了，阻塞
            while (count == 0){
                try {
                    FrameworkLogUtil.d( "get next count == 0 await");
                    notEmpty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            msg = mMessages[takeIndex];
            takeIndex = (++takeIndex >= mMessages.length? 0:takeIndex);
            count--;
            notFull.signalAll();
            FrameworkLogUtil.d( "next msg = %s, count = %s--->, notify push",msg,count);
            return msg;
        }finally {
            mLock.unlock();
        }
    }
}

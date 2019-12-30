package com.tesla.framework.component.okhttp;

import com.tesla.framework.common.util.FrameworkLogUtil;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jerry on 2019-12-29.
 */
public class ThreadManager extends DelayQueue {

    private ThreadPoolExecutor mExecutors;

    private static volatile ThreadManager instance = null;
    private ThreadManager(){
        mExecutors = new ThreadPoolExecutor(3,10,15, TimeUnit.SECONDS,

                    new ArrayBlockingQueue<Runnable>(4),
                    new RejectedExecutionHandler(){
                        @Override
                        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                            //将拒绝的线程重新加入队列
                            addTask(r);
                        }
                    });
        mExecutors.execute(coreThread);
        mExecutors.execute(delayThread);

    }
    public static ThreadManager getInstance() {
        if (instance == null) {
            synchronized (ThreadManager.class) {
                if (instance == null){
                    instance = new ThreadManager();
                }
            }
        }
        return instance;
    }


    private LinkedBlockingQueue<Runnable> mQueue = new LinkedBlockingQueue<>();


    private DelayQueue<HttpTask> mDelayQueue = new DelayQueue<>();



    public void addTask(Runnable runnable){
        if (runnable != null && !mQueue.contains(runnable)){
            try {
                mQueue.put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addDelayTask(HttpTask task){
        if (task != null){
            mDelayQueue.offer(task);
        }


    }

    //创建呼叫员线程，不停读取
    private Runnable coreThread = new Runnable() {
        @Override
        public void run() {
            Runnable runnable = null;
            while (true){
                try {
                    runnable = mQueue.take();

                    if (runnable != null){
                        mExecutors.execute(runnable);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    };



    private Runnable delayThread = new Runnable() {
        @Override
        public void run() {
            HttpTask task = null;
            while (true){
                try {
                    task = mDelayQueue.take();
                    //如果重试次数小于3次，丢给线程池处理
                    if (task.getRetryCount() < 3){
                        task.setRetryCount(task.getRetryCount() + 1);
                        mExecutors.execute(task);
                        FrameworkLogUtil.d("重试次数: %s, 时间:", task.getRetryCount(),System.currentTimeMillis());
                    }else {
                        FrameworkLogUtil.d("重试超过3次，不再执行");


                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    };
}

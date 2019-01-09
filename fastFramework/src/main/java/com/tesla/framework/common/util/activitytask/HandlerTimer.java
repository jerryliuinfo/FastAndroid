package com.tesla.framework.common.util.activitytask;

import android.os.Handler;
import java.util.concurrent.TimeUnit;

/**
 * Created by 01370340 on 2018/12/5.
 */
public class HandlerTimer {

    private Handler mHandler;

    public HandlerTimer(Handler handler) {
        mHandler = handler;
    }

    public TimeTask schedule(final Runnable runnable, long delay, TimeUnit timeUnit){

        TimeTask timeTask = new TimeTask() {
            @Override
            protected void doRun() {
                runnable.run();
            }
        };
        mHandler.postDelayed(timeTask,timeUnit.toMillis(delay));
        return timeTask;
    }

    public TimeTask schedule(final Runnable runnable, long delay, final long period, final TimeUnit timeUnit){

        final TimeTask timeTask = new TimeTask() {
            @Override
            protected void doRun() {
                runnable.run();
                mHandler.postDelayed(this,timeUnit.toMillis(period));
            }
        };
        mHandler.postDelayed(timeTask,timeUnit.toMillis(delay));
        return timeTask;
    }

    public void cancel(TimeTask timeTask){
        if (timeTask != null){
            timeTask.cancel();
            mHandler.removeCallbacks(timeTask);
        }
    }

    public void cancelAll(){
        mHandler.removeCallbacksAndMessages(null);
    }


    public static abstract class TimeTask implements Runnable{

        private volatile boolean isCanceled;

        protected abstract void doRun();

        public void cancel(){
            isCanceled = true;
        }
        @Override
        public void run() {
            if (!isCanceled){
                doRun();
            }
        }
    }
}

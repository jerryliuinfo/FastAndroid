package com.tesla.framework.common.util.date;

import android.util.Log;

import java.text.NumberFormat;

/**
 * Created by 01370340 on 2018/4/3.
 *
 */

public class TimeWatcher {
    public static final String TAG = TimeWatcher.class.getSimpleName();

    private String name;

    private long mStartTime;

    private long mEndTime;

    private long mElapsedTime;

    private NumberFormat mNumberFormat;

    public static final int MILLES_TO_NANOS = 1000000;

    public static final int SECOND_TO_NANOS = MILLES_TO_NANOS * 1000;

    public TimeWatcher(String name) {
        this.name = name;
    }

    private void reset(){
        mStartTime = 0;
        mEndTime = 0;
        mElapsedTime = 0;
    }

    public void start(){
        reset();
        mStartTime = System.nanoTime();
    }

    public void stop(){
        if (mStartTime != 0){
            mEndTime = System.nanoTime();
            mElapsedTime = mEndTime - mStartTime;
        }else {
            reset();
        }
    }

    public static TimeWatcher obtainAndStart(String name){
        TimeWatcher watcher = new TimeWatcher(name);
        watcher.start();
        return watcher;
    }


    public long getElapsedTimeAsNanos(){
        return mElapsedTime;
    }

    public long getElaspsedTimeAsMilles(){
        return mElapsedTime / MILLES_TO_NANOS;
    }


    public String getElapsedTimeAsString(){
        long s = mElapsedTime / SECOND_TO_NANOS;
        if (s > 0){
            return s +" s";
        }else {
            long ms = mElapsedTime / MILLES_TO_NANOS;
            if (ms > 0){
                return ms +" ms";
            }else {
                return (mElapsedTime % MILLES_TO_NANOS) + " ns";
            }
        }
    }

    public String stopAndPrint(){
        stop();
        String msg = String.format(name +"cost time : %s" , getElapsedTimeAsString());
        Log.d(TAG, msg);
        return msg;
    }


    public static void main(String[] args) {
        long value = 3543;
        System.out.println(value / 1000.0f);
        System.out.println(Math.round(value * 10) / (10.0f * 1000));
        System.out.println(Math.round(value * 10) / 10.0f);


    }
}

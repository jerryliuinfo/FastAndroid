package com.tesla.framework.common.util.view;

import android.text.TextUtils;

/**
 * @author wangtianbao
 * @Description: 防抖动计时器，为解决列表连续刷新同时滑动时的卡顿问题
 * @date 2016/6/14 16:13
 */
public class ThrottleFirstTimer {
    private long mThrottleTime=500;//500毫抖动时间
    private long mLastRefreshTime;
    private volatile boolean done;
    private volatile  boolean mDelay;
    private volatile boolean mNeedRefresh;
    private String mLastRefreshTag;

    ThrottleCallback mCallback;

    public void setRefreshCallback(ThrottleCallback callback){
        this.mCallback=callback;
    }

    public synchronized void reset(){
        mLastRefreshTime=now();
        done=false;
    }


    public  void delay(boolean b){
        mDelay=b;
        if(!mDelay&&mNeedRefresh&& !TextUtils.isEmpty(mLastRefreshTag)){
            mLastRefreshTime=now();
            if(mCallback!=null){
                mCallback.onRefresh(mLastRefreshTag);
            }
        }
    }
    /**
     * 新事件到来
     * @param force
     */
    public void nextEvent(boolean force,final String tag){
        if(force){//这次事件强制刷新
            if(mDelay){
                mNeedRefresh=true;
                mLastRefreshTag=tag;
            }else{
                mLastRefreshTime=now();
                if(mCallback!=null){
                    mCallback.onRefresh(tag);
                }
            }
        }else{
            if(mLastRefreshTime+mThrottleTime<now()){
                    if(mDelay){
                        mNeedRefresh=true;
                        mLastRefreshTag=tag;
                    }else{
                        mLastRefreshTime=now();
                        if(mCallback!=null){
                            mCallback.onRefresh(tag);
                        }
                    }
            }
        }
    }

    /**
     * 最后一个事件，强制并延迟刷新
     */
    public void lastEvent(final String tag){
        nextEvent(true,tag);
    }

    public static long now(){
        return System.currentTimeMillis();
    }

    public  interface ThrottleCallback{
        void onRefresh(final String tag);
    }
}

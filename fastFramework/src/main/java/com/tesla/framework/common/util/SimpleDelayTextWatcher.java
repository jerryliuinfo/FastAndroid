package com.tesla.framework.common.util;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;

/**
 * Created by 01370340 on 2018/8/2.
 * 需要对{@link SimpleDelayTextWatcher#afterTextChanged(Editable)}延迟执行，
 * 放置多次重复执行
 */

public abstract class SimpleDelayTextWatcher extends SimpleTextWatcher {
    private static final int RC_SEARCH = 6768;
    private int INTERVAL = 500; //输入时间间隔为300毫秒

    public SimpleDelayTextWatcher(int interval){
        this.INTERVAL = interval;
    }
    public SimpleDelayTextWatcher(){
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == RC_SEARCH) {
                afterTextChangedDelay((Editable) msg.obj);
            }
        }
    };


    @Override
    public void afterTextChanged(Editable s) {
        if (mHandler.hasMessages(RC_SEARCH)) {
            mHandler.removeMessages(RC_SEARCH);
        }
        Message msg = Message.obtain();
        msg.what = RC_SEARCH;
        msg.obj = s;
        mHandler.sendMessageDelayed(msg, INTERVAL);
    }


    public abstract void afterTextChangedDelay(Editable s);
}

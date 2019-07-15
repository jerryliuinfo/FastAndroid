package com.tesla.framework.ui.widget;

import android.os.SystemClock;
import android.view.View;

import com.tesla.framework.common.util.log.NLog;

/**
 * Created by 01370340 on 2017/12/23.
 */

public abstract class DoubleClickListener implements View.OnClickListener {
    private static final int MIN_CLICK_INTERVAL = 500;
    private int mInterval = MIN_CLICK_INTERVAL;
    private int mId = -1;
    private long mLastClickTime;

    public DoubleClickListener() {
    }



    @Override
    public void onClick(View v) {
        int id = v.getId();
        long currentTime = SystemClock.elapsedRealtime();
        if (mId != id){
            mId = id;
            mLastClickTime = currentTime;
            onDoubleClick(v);
            return;
        }
        if (currentTime - mLastClickTime > getMinClickInterval()){
            mLastClickTime = currentTime;
            onDoubleClick(v);
            NLog.d("onDoubleClick");
            return;
        }else {
            NLog.d("间隔过短，不处理");
        }
    }

    protected abstract void onDoubleClick(View v);


    protected int setMinClickInterval(){
        return MIN_CLICK_INTERVAL;
    }

    private int getMinClickInterval(){
        if (mInterval != setMinClickInterval()){
            return setMinClickInterval();
        }
        return mInterval;
    }
}

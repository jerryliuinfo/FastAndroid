package com.tesla.framework.common.util.view;

import android.view.View;

/**
 * Created by 01370340 on 2017/10/4.
 */

public abstract class NoDoubleClickListener implements View.OnClickListener {
    private final int MIN_CLICK_DELAY_TIME = 1000;
    private long mLastClickTime = 0;


    @Override
    public void onClick(View v) {
        long nowTime = System.currentTimeMillis();
        if (nowTime - mLastClickTime >= MIN_CLICK_DELAY_TIME){
            mLastClickTime = nowTime;
            onNoDoublcClick(v);
        }
    }

    public abstract void onNoDoublcClick(View view);
}

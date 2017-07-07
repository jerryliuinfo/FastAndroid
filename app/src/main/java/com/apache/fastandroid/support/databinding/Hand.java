package com.apache.fastandroid.support.databinding;

import android.view.View;

import com.tesla.framework.common.util.log.NLog;

/**
 * Created by jerryliu on 2017/7/6.
 */

public class Hand {
    public static final String TAG = Hand.class.getSimpleName();
    public void onClickName(View view) {
        NLog.d(TAG, "onClickName ");
    }
    public void onClickmore(View view) {
        NLog.d(TAG, "onClickmore ");
    }
}

package com.apache.fastandroid.topic.support.databinding;

import android.view.View;

import com.tesla.framework.common.util.log.FastLog;

/**
 * Created by jerryliu on 2017/7/6.
 */

public class Hand {
    public static final String TAG = Hand.class.getSimpleName();
    public void onClickName(View view) {
        FastLog.d(TAG, "onClickName ");
    }
    public void onClickmore(View view) {
        FastLog.d(TAG, "onClickmore ");
    }
}

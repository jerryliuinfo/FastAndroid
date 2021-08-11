package com.apache.fastandroid.widget;

/**
 * Created by Jerry on 2021/8/5.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.tesla.framework.common.util.log.NLog;

public  class MyViewGroup extends ViewGroup {
    public static final String TAG = MyViewGroup.class.getSimpleName();
    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View child = getChildAt(0);
//        int left = (r-l)/2 - child.getMeasuredWidth() / 2;
        int centerX = (r-l)/2 ;
        int centerY = (b-t)/2 ;
        NLog.d(TAG, "onLayout child width:%s, height: %s",child.getMeasuredWidth(),child.getMeasuredHeight());
        child.layout(centerX-100,centerY - 100, centerX+100,centerY + 100);
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }
}

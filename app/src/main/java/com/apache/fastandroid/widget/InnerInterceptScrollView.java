package com.apache.fastandroid.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.tesla.framework.common.util.log.NLog;

/**
 * Created by Jerry on 2021/8/6.
 * 外部拦截法处理滑动
 */
public class InnerInterceptScrollView extends ScrollView {
    public static final String TAG = InnerInterceptScrollView.class.getSimpleName();
    public InnerInterceptScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN){
            NLog.d(TAG, "InnerInterceptScrollView ACTION_DOWN, don't intercept ");
            return false;
        }else {
            NLog.d(TAG, "InnerInterceptScrollView NOT ACTION_DOWN, intercept ");
            return true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        NLog.d(TAG, "InnerInterceptScrollView onTouchEvent ");

        return super.onTouchEvent(ev);
    }
}

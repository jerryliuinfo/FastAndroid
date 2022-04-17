package com.tesla.framework.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.blankj.utilcode.util.ActivityUtils;
import com.tesla.framework.ui.activity.BaseVmActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

/**
 * Created by jerryliu on 2017/3/28.
 */

public class CustomToolbar extends Toolbar {
    public CustomToolbar(Context context) {
        super(context);
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private long lastClickTime;
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean handler = super.onTouchEvent(ev);
        if(ev.getAction() == MotionEvent.ACTION_UP) {
            if(this.lastClickTime != 0L && System.currentTimeMillis() - this.lastClickTime <= 500L) {
                Activity activity = ActivityUtils.getTopActivity();
                if (activity != null && activity instanceof BaseVmActivity){
                    BaseVmActivity baseActivity = (BaseVmActivity) activity;
                    if(baseActivity != null && baseActivity instanceof CustomToolbar.OnToolbarDoubleClickListener) {
                        baseActivity.OnToolbarDoubleClick();
                    }
                }
            }

            this.lastClickTime = System.currentTimeMillis();
        }

        return handler;
    }



    public interface OnToolbarDoubleClickListener{
        boolean OnToolbarDoubleClick();
    }
}

package com.tesla.framework.ui.widget;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.tesla.framework.ui.activity.BaseActivity;

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
                BaseActivity activity = BaseActivity.getRunningActivity();
                if(activity != null && activity instanceof CustomToolbar.OnToolbarDoubleClickListener) {
                    activity.OnToolbarDoubleClick();
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

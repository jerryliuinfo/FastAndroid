package com.tesla.framework.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.MotionEvent;

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
        if (ev.getAction() == MotionEvent.ACTION_UP){

        }
        return super.onTouchEvent(ev);
    }



    public interface OnToolbarDoubleClickListener{
        boolean OnToolbarDoubleClick();
    }
}

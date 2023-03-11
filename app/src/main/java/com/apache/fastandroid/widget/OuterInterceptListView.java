
package com.apache.fastandroid.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

import com.tesla.framework.component.logger.Logger;

/**
 * Created by Jerry on 2021/8/6.
 */
public class OuterInterceptListView extends ListView {
    public static final String TAG = OuterInterceptListView.class.getSimpleName();
    public OuterInterceptListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Logger.d( "CustomListView onTouchEvent action: %s", ev.getAction());

        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Logger.d(  "CustomListView onInterceptTouchEvent action: %s", ev.getAction());
        return super.onInterceptTouchEvent(ev);
    }
}


package com.apache.fastandroid.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by Jerry on 2021/8/6.
 */
public class InnerInterceptListView extends ListView {
    public static final String TAG = InnerInterceptListView.class.getSimpleName();
    public InnerInterceptListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        return super.onTouchEvent(ev);
    }

    private int mLastY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;

            case MotionEvent.ACTION_MOVE:

                //满足listView滑动到顶部，如果继续下滑，那就允许scrollView拦截事件
                if (getFirstVisiblePosition() == 0 && (ev.getY() - mLastY) > 0) {
                    //允许父ScrollView拦截事件
                    getParent().requestDisallowInterceptTouchEvent(false);

                }
                //满足listView滑动到底部，如果继续上滑，允许scrollView拦截事件
                else if (getLastVisiblePosition() == getCount() - 1 && (ev.getY() - mLastY) < 0) {
                    //允许ScrollView拦截事件
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    //其它情形时不允ScrollView拦截事件
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                mLastY = y;
                break;

            case MotionEvent.ACTION_UP:
                break;

            default:
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }
}

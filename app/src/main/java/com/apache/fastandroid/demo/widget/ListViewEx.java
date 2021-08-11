package com.apache.fastandroid.demo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by blueberry on 2016/6/20.
 * 内部拦截事件
 */
public class ListViewEx extends ListView {

    private int lastXIntercepted, lastYIntercepted;

    private HorizontalEx2 mHorizontalEx2;

    public ListViewEx(Context context) {
        super(context);
    }

    public ListViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HorizontalEx2 getmHorizontalEx2() {
        return mHorizontalEx2;
    }

    public void setmHorizontalEx2(HorizontalEx2 mHorizontalEx2) {
        this.mHorizontalEx2 = mHorizontalEx2;
    }

    /**
     * 使用 outter.requestDisallowInterceptTouchEvent();
     * 来决定父控件是否对事件进行拦截
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mHorizontalEx2.requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                final int deltaX = x-lastYIntercepted;
                final int deltaY = y-lastYIntercepted;
                if(Math.abs(deltaX)>Math.abs(deltaY)){
                    mHorizontalEx2.requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        lastXIntercepted = x;
        lastYIntercepted = y;
        return super.dispatchTouchEvent(ev);
    }
}

package com.apache.fastandroid.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;

import com.tesla.framework.common.util.log.NLog;

/**
 * Created by Jerry on 2021/8/6.
 * 外部拦截法处理滑动
 */
public class OuterInterceptScrollView extends ScrollView {
    public static final String TAG = OuterInterceptScrollView.class.getSimpleName();

    private ListView listView;

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    public OuterInterceptScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
//        NLog.d(TAG, "CustomScrollView onTouchEvent ");
        return super.onTouchEvent(ev);
    }

    private float mLastY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        NLog.d(TAG, "CustomScrollView onInterceptTouchEvent ");

        boolean intercept = false;
        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                listView = (ListView) ((ViewGroup) getChildAt(0)).getChildAt(1);
                //ListView滑动到顶部，且继续下滑，让scrollView拦截事件
                if (listView.getFirstVisiblePosition() == 0 && (ev.getY() - mLastY) > 0) {
                    //scrollView拦截事件
                    intercept = true;
                    NLog.d(TAG, "list view's first position, interrupt");

                }
                //listView滑动到底部，如果继续上滑，就让scrollView拦截事件
                else if (listView.getLastVisiblePosition() == listView.getCount() - 1 && (ev.getY() - mLastY) < 0) {
                    //scrollView拦截事件
                    intercept = true;
                    NLog.d(TAG, "list view's last position, interrupt");

                } else {
                    //不允许scrollView拦截事件
                    intercept = false;
                    NLog.d(TAG, "list view's range, don't intercept");

                }

                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
        }
        mLastY = ev.getY();
        NLog.d(TAG, "CustomScrollView onInterceptTouchEvent intercept: %s",intercept);
        return intercept;



    }
}

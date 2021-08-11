package com.apache.fastandroid.demo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by blueberry on 2016/6/20.
 *
 * 解决交错的滑动冲突
 *
 * 外部拦截法
 */
public class HorizontalEx extends ViewGroup {

    private static final String TAG = "HorizontalEx";

    private boolean isFirstTouch = true;
    private int childIndex;
    private int childCount;
    private int lastXIntercept, lastYIntercept, lastX, lastY;

    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;

    public HorizontalEx(Context context) {
        super(context);
        init();
    }

    public HorizontalEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        childCount = getChildCount();
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        if (childCount == 0) {
            setMeasuredDimension(0, 0);
        } else if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            width = childCount * getChildAt(0).getMeasuredWidth();
            height = getChildAt(0).getMeasuredHeight();
            setMeasuredDimension(width, height);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = childCount * getChildAt(0).getMeasuredWidth();
            setMeasuredDimension(width, height);
        } else {
            height = getChildAt(0).getMeasuredHeight();
            setMeasuredDimension(width, height);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = 0;
        for (int i = 0; i < getChildCount(); i++) {
            final View child = getChildAt(i);
            child.layout(left + l, t, r + left, b);
            left += child.getMeasuredWidth();
        }
    }

    /**
     * 拦截事件
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            /*如果拦截了Down事件,则子类不会拿到这个事件序列*/
            case MotionEvent.ACTION_DOWN:
                lastXIntercept = x;
                lastYIntercept = y;
                intercepted = false;
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                    intercepted = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                final int deltaX = x - lastXIntercept;
                final int deltaY = y - lastYIntercept;
                /*根据条件判断是否拦截该事件*/
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    intercepted = true;
                } else {
                    intercepted = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;

        }
        lastXIntercept = x;
        lastYIntercept = y;
        return intercepted;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        mVelocityTracker.addMovement(event);
        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                /*因为这里父控件拿不到Down事件，所以使用一个布尔值，
                    当事件第一次来到父控件时，对lastX,lastY赋值*/
                if (isFirstTouch) {
                    lastX = x;
                    lastY = y;
                    isFirstTouch = false;
                }
                final int deltaX = x - lastX;
                scrollBy(-deltaX, 0);
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                final int childWidth = getChildAt(0).getWidth();
                mVelocityTracker.computeCurrentVelocity(1000,
                        configuration.getScaledMaximumFlingVelocity());
                float xVelocity = mVelocityTracker.getXVelocity();
                if (Math.abs(xVelocity) > configuration.getScaledMinimumFlingVelocity()) {
                    childIndex = xVelocity < 0 ? childIndex + 1 : childIndex - 1;
                } else {
                    childIndex = (scrollX + childWidth / 2) / childWidth;
                }
                childIndex = Math.min(getChildCount() - 1, Math.max(childIndex, 0));
                smoothScrollBy(childIndex * childWidth - scrollX, 0);
                mVelocityTracker.clear();
                isFirstTouch = true;
                break;
        }

        lastX = x;
        lastY = y;
        return true;
    }

    void smoothScrollBy(int dx, int dy) {
        mScroller.startScroll(getScrollX(), getScrollY(), dx, dy, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mVelocityTracker.recycle();
    }
}

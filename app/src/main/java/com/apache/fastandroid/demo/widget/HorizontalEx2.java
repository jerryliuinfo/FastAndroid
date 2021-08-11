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
 * <p/>
 * 内部拦截
 * 和 ListViewEx配合使用
 */
public class HorizontalEx2 extends ViewGroup {

    private int lastX, lastY;
    private int childIndex;
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;

    public HorizontalEx2(Context context) {
        super(context);
        init();
    }

    public HorizontalEx2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalEx2(Context context, AttributeSet attrs, int defStyleAttr) {
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
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int childCount = getChildCount();
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        if (childCount == 0) {
            setMeasuredDimension(0, 0);
        } else if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            height = getChildAt(0).getMeasuredHeight();
            width = childCount * getChildAt(0).getMeasuredWidth();
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
        int leftOffset = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.layout(l + leftOffset, t, r + leftOffset, b);
            leftOffset += child.getMeasuredWidth();
        }
    }

    /**
     * 不拦截Down事件，其他一律拦截
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (!mScroller.isFinished()) {
                mScroller.abortAnimation();
                return true;
            }
            return false;
        } else {
            return true;
        }
    }

    private boolean isFirstTouch = true;

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
                if (isFirstTouch) {
                    isFirstTouch = false;
                    lastY = y;
                    lastX = x;
                }
                final int deltaX = x - lastX;
                scrollBy(-deltaX, 0);
                break;
            case MotionEvent.ACTION_UP:
                isFirstTouch = true;
                int scrollX = getScrollX();
                mVelocityTracker.computeCurrentVelocity(1000, configuration.getScaledMaximumFlingVelocity());
                float mVelocityX = mVelocityTracker.getXVelocity();
                if (Math.abs(mVelocityX) > configuration.getScaledMinimumFlingVelocity()) {
                    childIndex = mVelocityX < 0 ? childIndex + 1 : childIndex - 1;
                } else {
                    childIndex = (scrollX + getChildAt(0).getWidth() / 2) / getChildAt(0).getWidth();
                }
                childIndex = Math.min(getChildCount() - 1, Math.max(0, childIndex));
                smoothScrollBy(childIndex*getChildAt(0).getWidth()-scrollX,0);
                mVelocityTracker.clear();
                break;
        }

        lastX = x;
        lastY = y;
        return true;
    }

    private void smoothScrollBy(int dx, int dy) {
        mScroller.startScroll(getScrollX(), getScrollY(), dx, dy,500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mVelocityTracker.recycle();
    }
}

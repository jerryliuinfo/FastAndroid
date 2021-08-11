package com.apache.fastandroid.demo.widget.refresh;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Scroller;

import com.apache.fastandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by blueberry on 2016/6/22.
 * 结合内部类 ListVieEx
 * 内部拦截法，同向
 */
public class RefreshLayoutBase2 extends ViewGroup {

    private static final String TAG = "RefreshLayoutBase2";

    private static List<String> datas;

    static {
        datas = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            datas.add("数据—" + i);
        }
    }

    private ViewGroup headView;
    private ListViewEx lv;

    private int lastY;
    public int mInitScrollY;

    private Scroller mScroller;

    public RefreshLayoutBase2(Context context) {
        this(context, null);
    }

    public RefreshLayoutBase2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public RefreshLayoutBase2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
        setupHeadView(context);
        setupContentView(context);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int finalHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            finalHeight += child.getMeasuredHeight();
        }

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            widthSize = getChildAt(0).getMeasuredWidth();
            setMeasuredDimension(widthSize, finalHeight);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = getChildAt(0).getMeasuredWidth();
            setMeasuredDimension(widthSize, height);
        } else {
            setMeasuredDimension(widthSize, finalHeight);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int topOffset = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.layout(getPaddingLeft(), getPaddingTop() + topOffset, r,
                    getPaddingTop() + child.getMeasuredHeight() + topOffset);
            topOffset += child.getMeasuredHeight();
        }
        mInitScrollY = headView.getMeasuredHeight() + getPaddingTop();
        scrollTo(0, mInitScrollY);

    }

    /**
     * 不拦截Down 其他一律拦截
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) return false;
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                final int deltaY = y-lastY;
                Log.i(TAG, "onTouchEvent: deltaY: "+deltaY);
                if (deltaY >= 0 && lv.isTop() && getScrollY() - deltaY >=getPaddingTop()) {
                        scrollBy(0, -deltaY);
                }
                break;
            case MotionEvent.ACTION_UP:
                this.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mScroller.startScroll(0,getScrollY(),0,mInitScrollY-getScrollY());
                        invalidate();
                    }
                },2000);
                break;
        }

        lastY = y ;
        return true;
    }

    private void setupHeadView(Context context) {
        headView = (ViewGroup) View.inflate(context, R.layout.fresh_head_view, null);
        headView.setBackgroundColor(Color.RED);
        LayoutParams params =
                new LayoutParams(LayoutParams.MATCH_PARENT, 300);
        addView(headView, params);
    }

    public void setupContentView(Context context) {
        lv = new ListViewEx(context, this);
        lv.setBackgroundColor(Color.BLUE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, datas);
        lv.setAdapter(adapter);
        addView(lv, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }

    public static class ListViewEx extends ListView {

        private RefreshLayoutBase2 outter;

        public ListViewEx(Context context, RefreshLayoutBase2 outter) {
            super(context);
            this.outter = outter;
        }

        public ListViewEx(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public ListViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        /**
         * 使用 outter.requestDisallowInterceptTouchEvent();
         * 来决定父控件是否对事件进行拦截
         * @param ev
         * @return
         */
        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    outter.requestDisallowInterceptTouchEvent(true);
                    break;
                case MotionEvent.ACTION_MOVE:

                    if ( isTop() && outter.getScrollY() <= outter.mInitScrollY) {
                        outter.requestDisallowInterceptTouchEvent(false);
                    }
                    break;

            }
            return super.dispatchTouchEvent(ev);
        }

        public boolean isTop() {
            return getFirstVisiblePosition() ==0;
        }
    }
}

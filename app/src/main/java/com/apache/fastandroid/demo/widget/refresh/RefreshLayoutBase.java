package com.apache.fastandroid.demo.widget.refresh;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Scroller;
import android.widget.TextView;

import com.apache.fastandroid.R;


/**
 * Created by blueberry on 2016/6/21.
 *
 *外部拦截(同向)
 *
 */
public abstract class RefreshLayoutBase<T extends View> extends ViewGroup {

    private static final String TAG = "RefreshLayoutBase";

    public static final int STATUS_LOADING = 1;
    public static final int STATUS_RELEASE_TO_REFRESH = 2;
    public static final int STATUS_PULL_TO_REFRESH = 3;
    public static final int STATUS_IDLE = 4;
    public static final int STATUS_LOAD_MORE =5;
    private static int SCROLL_DURATION =500;

    protected ViewGroup mHeadView;
    protected ViewGroup mFootView;
    private T contentView;
    private ProgressBar headProgressBar;
    private TextView headTv;
    private ProgressBar footProgressBar;
    private TextView footTv;

    private boolean isFistTouch = true;

    protected int currentStatus = STATUS_IDLE;
    private int mScreenWidth;
    private int mScreenHeight;
    private int mLastXIntercepted;
    private int mLastYIntercepted;
    private int mLastX;
    private int mLastY;
    protected int mInitScrollY = 0;
    private int mTouchSlop;

    protected Scroller mScoller;

    private OnRefreshListener mOnRefreshListener;

    public RefreshLayoutBase(Context context) {
        this(context, null);
    }

    public RefreshLayoutBase(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshLayoutBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getScreenSize();
        initView();
        mScoller = new Scroller(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setPadding(0, 0, 0, 0);
    }

    public void setContentView(T view) {
        addView(view, 1);
    }

    public OnRefreshListener getOnRefreshListener() {
        return mOnRefreshListener;
    }

    public void setOnRefreshListener(OnRefreshListener mOnRefreshListener) {
        this.mOnRefreshListener = mOnRefreshListener;
    }

    private void initView() {
        setupHeadView();
        setupFootView();
    }

    private void getScreenSize() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        mScreenWidth = metrics.widthPixels;
        mScreenHeight = metrics.heightPixels;
    }

    private int dp2px(int dp) {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }

    /**
     * 设置头布局
     */
    private void setupHeadView() {
        mHeadView = (ViewGroup) View.inflate(getContext(), R.layout.fresh_head_view, null);
        mHeadView.setBackgroundColor(Color.RED);
        headProgressBar = (ProgressBar) mHeadView.findViewById(R.id.head_progressbar);
        headTv = (TextView) mHeadView.findViewById(R.id.head_tv);
        /*设置 实际高度为 1/4 ，但内容区域只有 100dp*/
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, mScreenHeight / 4);
        mHeadView.setLayoutParams(layoutParams);
        mHeadView.setPadding(0, mScreenHeight / 4 - dp2px(100), 0, 0);
        addView(mHeadView);
    }

    /**
     * 设置尾布局
     */
    private void setupFootView() {
        mFootView = (ViewGroup) View.inflate(getContext(), R.layout.fresh_foot_view, null);
        mFootView.setBackgroundColor(Color.BLUE);
        footProgressBar = (ProgressBar) mFootView.findViewById(R.id.fresh_foot_progressbar);
        footTv = (TextView) mFootView.findViewById(R.id.fresh_foot_tv);
        addView(mFootView);
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
        mInitScrollY = mHeadView.getMeasuredHeight() + getPaddingTop();
        scrollTo(0, mInitScrollY);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastXIntercepted = x;
                mLastYIntercepted = y;
                break;
            case MotionEvent.ACTION_MOVE:
                final int deltaY = x - mLastYIntercepted;
                if (isTop() && deltaY > 0 && Math.abs(deltaY) > mTouchSlop) {
                    /*下拉*/
                    intercepted = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        mLastXIntercepted = x;
        mLastYIntercepted = y;
        return intercepted;
    }

    private void doRefresh() {
        Log.i(TAG, "doRefresh: ");
        if (currentStatus == STATUS_RELEASE_TO_REFRESH) {
            mScoller.startScroll(0, getScrollY(), 0, mInitScrollY - getScrollY(), SCROLL_DURATION);
            currentStatus = STATUS_IDLE;
        } else if (currentStatus == STATUS_PULL_TO_REFRESH) {
            mScoller.startScroll(0,getScrollY(),0,0-getScrollY(),SCROLL_DURATION);
            if (null != mOnRefreshListener) {
                currentStatus = STATUS_LOADING;
                mOnRefreshListener.refresh();
            }
        }
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScoller.isFinished()) {
                    mScoller.abortAnimation();
                }
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                if (isFistTouch) {
                    isFistTouch = false;
                    mLastX = x;
                    mLastY = y;
                }
                final int deltaY = y - mLastY;
                if (currentStatus != STATUS_LOADING) {
                    changeScrollY(deltaY);
                }
                break;
            case MotionEvent.ACTION_UP:
                isFistTouch = true;
                doRefresh();
                break;
        }

        mLastX = x;
        mLastY = y;
        return true;
    }

    private void changeScrollY(int deltaY) {
        Log.i(TAG, "changeScrollY: ");
        int curY = getScrollY();
        if (deltaY > 0) {
            /*下拉*/
            if (curY - deltaY > getPaddingTop()) {
                scrollBy(0, -deltaY);
            }
        } else {
            /*上拉*/
            if (curY - deltaY <= mInitScrollY) {
                scrollBy(0, -deltaY);
            }
        }

        curY = getScrollY();
        int slop = mInitScrollY / 2;
        if (curY > 0 && curY <=slop) {
            currentStatus = STATUS_PULL_TO_REFRESH;
        } else if (curY > 0 && curY >= slop) {
            currentStatus = STATUS_RELEASE_TO_REFRESH;
        }
    }

    @Override
    public void computeScroll() {
        if (mScoller.computeScrollOffset()) {
            scrollTo(mScoller.getCurrX(), mScoller.getCurrY());
            postInvalidate();
        }
    }

    /**
     * 加载完成调用这个方法
     */
    public void refreshComplete() {
        mScoller.startScroll(0, getScrollY(), 0, mInitScrollY - getScrollY(), SCROLL_DURATION);
        currentStatus = STATUS_IDLE;
        invalidate();
    }

    /**
     * 显示 Footer
     */
    public void showFooter() {
        if(currentStatus==STATUS_LOAD_MORE) return ;
        currentStatus = STATUS_LOAD_MORE ;
        mScoller.startScroll(0, getScrollY(), 0, mFootView.getMeasuredHeight()
                , SCROLL_DURATION);
        invalidate();

    }


    /**
     * loadMore完成之后调用
     */
    public void footerComplete() {
        mScoller.startScroll(0, getScrollY(), 0, mInitScrollY - getScrollY(), SCROLL_DURATION);
        invalidate();
        currentStatus = STATUS_IDLE;
    }

    public interface OnRefreshListener {
        void refresh();
    }

    abstract boolean isTop();

    abstract boolean isBottom();

}

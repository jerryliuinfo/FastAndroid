package com.apache.fastandroid.demo.widget.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by blueberry on 2016/6/21.
 *
 * RefreshLayoutBase 的一个实现类
 */
public class RefreshListView extends RefreshLayoutBase<ListView> {

    private static final String TAG = "RefreshListView";

    private ListView listView;
    private OnLoadListener loadListener;

    public RefreshListView(Context context) {
        super(context);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ListView getListView() {
        return listView;
    }

    public void setListView(final ListView listView) {
        this.listView = listView;
        setContentView(listView);

        this.listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                /*这里存在一个bug: 当listView滑动到底部的时候，如果下拉也会出现footer
                * 这是因为，暂时还没有想到如何判断是下拉还是上拉。
                * 如果要解决此问题，我觉得应该重写listView 的onTouchEvent来判断手势方向
                * 次模块主要解决竖向滑动冲突，故现将此问题放下。
                * */
                if (currentStatus == STATUS_IDLE
                        && getScrollY() <= mInitScrollY && isBottom()
                        ) {
                    showFooter();
                    if (null != loadListener) {
                        loadListener.onLoadMore();
                    }
                }

            }
        });
    }

    public OnLoadListener getLoadListener() {
        return loadListener;
    }

    public void setLoadListener(OnLoadListener loadListener) {
        this.loadListener = loadListener;
    }

    @Override
    boolean isTop() {
        return listView.getFirstVisiblePosition() == 0
                && getScrollY() <= mHeadView.getMeasuredHeight();
    }

    @Override
    boolean isBottom() {
        return listView.getLastVisiblePosition() == listView.getAdapter().getCount() - 1;
    }

    public interface OnLoadListener {
        void onLoadMore();
    }
}

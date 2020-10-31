package com.tesla.framework.ui.fragment;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tesla.framework.R;
import com.tesla.framework.support.inject.ViewInject;

import java.io.Serializable;

/**
 * Created by jerryliu on 2017/4/9.
 * 带下啦刷新效果的
 */

public abstract class ARecycleViewSwipeRefreshFragment<T extends Serializable,Ts extends Serializable,Header extends Serializable>
        extends ARecycleViewFragment<T,Ts,Header> implements SwipeRefreshLayout.OnRefreshListener {

    @ViewInject(idStr = "swipeRefreshLayout")
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public int inflateContentView() {
        return R.layout.comm_ui_recycleview_swiperefresh;
    }

    @Override
    protected void setUpRefreshView() {
        super.setUpRefreshView();
        setupSwipeRefreshLayout();

    }

    @Override
    protected void onRefreshViewFinished(RefreshMode mode) {
        super.onRefreshViewFinished(mode);
        getSwipeRefreshLayout().setRefreshing(false);
    }

    protected void setupSwipeRefreshLayout() {
        getSwipeRefreshLayout().setOnRefreshListener(this);
        getSwipeRefreshLayout().setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    @Override
    public void onRefresh() {
        onPullDownToRefresh();
    }



}

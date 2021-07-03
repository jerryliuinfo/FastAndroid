package com.tesla.framework.ui.fragment;

import android.view.View;

import com.tesla.framework.R;
import com.tesla.framework.databinding.ComUiFragmentRecycleviewSwiperefreshlayoutBinding;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Created by jerryliu on 2017/4/9.
 * 带下啦刷新效果的
 */

public abstract class ARecycleViewSwipeRefreshFragmentNew extends ARecycleViewFragmentNew<ComUiFragmentRecycleviewSwiperefreshlayoutBinding> implements SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public int inflateContentView() {
        return R.layout.comm_ui_recycleview_swiperefresh_new;
    }

    @Override
    public void bindUI(View rootView) {
        super.bindUI(rootView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        setupSwipeRefreshLayout();
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



}

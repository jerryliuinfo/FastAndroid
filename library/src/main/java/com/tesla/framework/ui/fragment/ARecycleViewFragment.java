package com.tesla.framework.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.tesla.framework.R;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.fragment.adpater.BasicRecycleViewAdapter;
import com.tesla.framework.ui.fragment.adpater.IPagingAdapter;
import com.tesla.framework.ui.fragment.itemview.IITemView;
import com.tesla.framework.ui.fragment.itemview.header.AHeaderItemViewCreator;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 维护RecycleView
 *
 * Created by JerryLiu on 17/04/08.
 */
public abstract class ARecycleViewFragment<T extends Serializable, Ts extends Serializable, Header extends Serializable>
                            extends APagingFragment<T, Ts, Header, RecyclerView>
                            implements AdapterView.OnItemClickListener {

    @ViewInject(idStr = "recycleview")
    RecyclerView mRecycleView;



    @Override
    public int inflateContentView() {
        return R.layout.comm_ui_recycleview;
    }

    @Override
    public RecyclerView getRefreshView() {
        return mRecycleView;
    }

    @Override
    protected void setUpRefreshView() {
        super.setUpRefreshView();
        getRefreshView().addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                ARecycleViewFragment.this.onScrollStateChanged(newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }

        });
        getRefreshView().setLayoutManager(configLayoutManager());
    }



    @Override
    protected IPagingAdapter<T> newAdapter(ArrayList<T> datas) {
        return new BasicRecycleViewAdapter<>(getActivity(), datas, configItemViewCreator(), this );
    }

    /**
     * 默认是LinearLayoutManager
     *
     * @return
     */
    protected RecyclerView.LayoutManager configLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override
    protected void bindAdapter(IPagingAdapter adapter) {
        if (getRefreshView().getAdapter() == null) {
            getRefreshView().setAdapter((BasicRecycleViewAdapter) adapter);
        }

        if (((BasicRecycleViewAdapter) getAdapter()).getOnItemClickListener() != this) {
            ((BasicRecycleViewAdapter) getAdapter()).setOnItemClickListener(this);
        }
    }


    @Override
    protected void addFooterViewToRefreshView(IITemView<T> footerItemView) {
        super.addFooterViewToRefreshView(footerItemView);
        ((BasicRecycleViewAdapter) getAdapter()).addFooterView(footerItemView);
    }

    @Override
    protected void addHeaderViewToRefreshView(AHeaderItemViewCreator<Header> headerItemViewCreator) {
        super.addHeaderViewToRefreshView(headerItemViewCreator);
        ((BasicRecycleViewAdapter) getAdapter()).addHeaderViewCreator(headerItemViewCreator);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        NLog.d(TAG, "onItemClick postion = %s", position);
    }

    @Override
    protected void toLastReadPosition() {
        super.toLastReadPosition();
        if (getRefreshView() == null || TextUtils.isEmpty(getRefreshConfig().positionKey) ||
                getLastReadPosition() <= 0)
            return;

        if (getRefreshView().getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager manager = (LinearLayoutManager) getRefreshView().getLayoutManager();

            if (getAdapterItems().size() > getLastReadPosition()) {
                manager.scrollToPositionWithOffset(getLastReadPosition(), getLastReadTop() + getRefreshView().getPaddingTop());
            }
        }
    }

    @Override
    protected int getFirstVisiblePosition() {
        if (getRefreshView().getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager manager = (LinearLayoutManager) getRefreshView().getLayoutManager();

            return manager.findFirstVisibleItemPosition();
        }

        return 0;
    }



    /**
     * 初始化ListView
     *
     * @param items
     */
    public void setItems(ArrayList<T> items) {
        if (items == null)
            return;

        setViewVisiable(loadingLayout, View.GONE);
        setViewVisiable(loadFailureLayout, View.GONE);
        if (items.size() == 0 && emptyLayout != null) {
            setViewVisiable(emptyLayout, View.VISIBLE);
            setViewVisiable(contentLayout, View.GONE);
        }
        else {
            setViewVisiable(emptyLayout, View.GONE);
            setViewVisiable(contentLayout, View.VISIBLE);
        }
        setAdapterItems(items);
        if (getRefreshView().getAdapter() == null) {
            bindAdapter(getAdapter());
        }
        else {
            if (getRefreshView().getLayoutManager() instanceof LinearLayoutManager) {
                final LinearLayoutManager manager = (LinearLayoutManager) getRefreshView().getLayoutManager();
                manager.scrollToPositionWithOffset(0,0);
            }
            getAdapter().notifyDataSetChanged();
        }


    }

}

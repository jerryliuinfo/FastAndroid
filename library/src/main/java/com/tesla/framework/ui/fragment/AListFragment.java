package com.tesla.framework.ui.fragment;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tesla.framework.R;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.fragment.adpater.IPagingAdapter;
import com.tesla.framework.ui.fragment.itemview.IITemView;
import com.tesla.framework.ui.fragment.itemview.header.AHeaderItemViewCreator;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 维护ListView
 *
 */
public abstract class AListFragment<T extends Serializable, Ts extends Serializable, Header extends Serializable>
                                extends APagingFragment<T, Ts, Header, ListView> implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener {

    @ViewInject(idStr = "listView")
    ListView mListView;

    @Override
    public int inflateContentView() {
        return R.layout.comm_ui_list;
    }



    @Override
    protected void setUpRefreshView() {
        super.setUpRefreshView();
        // 设置事件
        getRefreshView().setOnScrollListener(this);
        getRefreshView().setOnItemClickListener(this);
    }

    @Override
    public ListView getRefreshView() {
        return mListView;
    }

    @Override
    protected IPagingAdapter<T> newAdapter(ArrayList<T> datas) {
        return new BasicListAdapter<>(this, datas);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        onScroll(firstVisibleItem, visibleItemCount, totalItemCount);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        onScrollStateChanged(scrollState);
    }

    @Override
    public void requestDataOutofdate() {
        getRefreshView().setSelectionFromTop(0, 0);

        super.requestDataOutofdate();
    }

    @Override
    protected int getFirstVisiblePosition() {
        return getRefreshView().getFirstVisiblePosition();
    }

    @Override
    protected void bindAdapter(IPagingAdapter adapter) {
        if (getRefreshView().getAdapter() == null)
            getRefreshView().setAdapter((BasicListAdapter) adapter);
    }


    @Override
    protected void addFooterViewToRefreshView(IITemView<T> footerItemView) {
        super.addFooterViewToRefreshView(footerItemView);
        getRefreshView().addFooterView(footerItemView.getConvertView());
    }

    @Override
    protected void addHeaderViewToRefreshView(AHeaderItemViewCreator<Header> headerItemViewCreator) {
        super.addHeaderViewToRefreshView(headerItemViewCreator);
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
            getRefreshView().setSelectionFromTop(0, 0);
            getAdapter().notifyDataSetChanged();
        }
    }

}

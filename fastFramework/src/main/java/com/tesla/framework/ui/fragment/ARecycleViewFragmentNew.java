package com.tesla.framework.ui.fragment;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tesla.framework.R;
import com.tesla.framework.common.util.log.NLog;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 维护RecycleView
 *
 * Created by JerryLiu on 17/04/08.
 */
public abstract class ARecycleViewFragmentNew<Q extends ViewDataBinding> extends BaseLifecycleFragment<Q>{
    @Override
    public int inflateContentView() {
        return R.layout.comm_ui_recycleview_new;
    }

    private RecyclerView mRecycleView;
    private BaseQuickAdapter adapter;

    @Override
    public void bindUI(View rootView) {
        super.bindUI(rootView);
        mRecycleView = findViewById(R.id.recycleview);
        setupRefreshView();
    }

    protected void setupRefreshView(){
        getRefreshView().setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX,
                                       int oldScrollY) {
                ARecycleViewFragmentNew.this.onScrollChange(v,scrollX,scrollY,oldScrollX,oldScrollY);
            }
        });
        getRefreshView().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                ARecycleViewFragmentNew.this.onScrollStateChanged(recyclerView,newState);
            }
        });
        adapter = createAdapter();
        mRecycleView.setLayoutManager(configLayoutManager());
        mRecycleView.setAdapter(adapter);
        adapter.setEnableLoadMore(true);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                onLoadMore();
            }
        },getRefreshView());
    }

    protected abstract void onLoadMore();


    protected abstract BaseQuickAdapter createAdapter();

    protected RecyclerView getRefreshView() {
        return mRecycleView;
    }

    public BaseQuickAdapter getAdapter() {
        return adapter;
    }

    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX,
                               int oldScrollY){
        NLog.d(TAG, "onScrollChange scrollX:%s, scrollY:%s, oldScrollX:%s, oldScrollY:%s", scrollX,scrollY,oldScrollX,oldScrollY);
    }

    public void onScrollStateChanged(RecyclerView recyclerView, int newState){
        NLog.d(TAG, "onScrollStateChanged recyclerView:%s, newState:%s", recyclerView,newState);
    }



    /**
     * 默认是LinearLayoutManager
     *
     * @return
     */
    protected RecyclerView.LayoutManager configLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    /**
     * 获取第一个可见的position
     * @return
     */
    protected int getFirstVisiblePosition() {
        if (getRefreshView().getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager manager = (LinearLayoutManager) getRefreshView().getLayoutManager();

            return manager.findFirstVisibleItemPosition();
        }

        return 0;
    }
}

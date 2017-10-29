package com.apache.fastandroid.novel.bookshelf;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apache.fastandroid.novel.bookshelf.view.RecommandViewCreator;
import com.apache.fastandroid.novel.find.bean.CollectionBeans;
import com.apache.fastandroid.novel.find.bean.RecommandBeans;
import com.apache.fastandroid.novel.find.bean.RecommendBook;
import com.apache.fastandroid.novel.support.event.RefreshCollectionListEvent;
import com.apache.fastandroid.novel.support.sdk.NovelSdk;
import com.apache.fastandroid.novel.support.sqlite.CollectionDB;
import com.apache.fastandroid.novel.support.util.NovelLog;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.support.bean.RefreshConfig;
import com.tesla.framework.ui.fragment.ARecycleViewSwipeRefreshFragment;
import com.tesla.framework.ui.fragment.itemview.IITemView;
import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by 01370340 on 2017/9/24.
 */

public class RecommandFragment extends ARecycleViewSwipeRefreshFragment<RecommendBook,RecommandBeans,RecommendBook> {

    public static RecommandFragment newFragment() {
        RecommandFragment fragment = new RecommandFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void setUpRefreshConfig(RefreshConfig refreshConfig) {
        super.setUpRefreshConfig(refreshConfig);
        //refreshConfig.disalbeFooterMore();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NovelLog.e("RecommandFragment onViewCreated-------");
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        NovelLog.e("RecommandFragment onDestroyView");
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected IItemViewCreator<RecommendBook> configItemViewCreator() {
        return new RecommandViewCreator(getActivity());
    }

    @Override
    public void requestData(RefreshMode mode) {
        super.requestData(mode);
        if (mode == RefreshMode.refresh){
            mode = RefreshMode.reset;
        }
        new LoadDataTask(mode).execute();
    }

    class LoadDataTask extends APagingTask<Void,Void,CollectionBeans>{

        public LoadDataTask(RefreshMode mode) {
            super(mode);
        }

        @Override
        protected List<RecommendBook> parseResult(CollectionBeans collectionBeans) {
            return collectionBeans.list;
        }

        @Override
        protected CollectionBeans workInBackground(RefreshMode mode, String previousPage, String nextPage, Void... params) throws TaskException {
            return NovelSdk.getInstance(getTaskCacheMode(this)).getCollection();
        }

        @Override
        protected void onSuccess(CollectionBeans collectionBeans) {
            super.onSuccess(collectionBeans);
        }
    }

    private boolean mDataChanged = false;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshCollectionList(RefreshCollectionListEvent event){
        NovelLog.d("refreshCollectionList--------->");
        mDataChanged = true;
        //requestData(RefreshMode.refresh);


        getAdapter().getDatas().clear();
        getAdapter().getDatas().addAll(CollectionDB.selectAll());
        //不加下面这句代码会导致，添加本地书籍的时候，部分书籍添加后直接崩溃
        //报错：Scrapped or attached views may not be recycled. isScrap:false isAttached:true
        getAdapter().notifyDataSetChanged();


    }

    @Override
    protected IItemViewCreator<RecommendBook> configFooterItemViewCreator() {
        return new IItemViewCreator<RecommendBook>() {
            @Override
            public View newContentView(LayoutInflater inflater, ViewGroup parent, int viewType) {
                return inflater.inflate(BookSelfFootView.LAY_RES,parent,false);
            }

            @Override
            public IITemView<RecommendBook> newItemView(View contentView, int viewType) {
                return new BookSelfFootView<>(getActivity(),contentView,null);
            }
        };

    }
}

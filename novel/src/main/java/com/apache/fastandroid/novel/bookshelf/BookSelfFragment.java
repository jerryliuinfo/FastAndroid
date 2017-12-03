package com.apache.fastandroid.novel.bookshelf;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.apache.fastandroid.novel.NovelHomeTabsFragment;
import com.apache.fastandroid.novel.R;
import com.apache.fastandroid.novel.bookshelf.view.BookSelfItemView;
import com.apache.fastandroid.novel.find.bean.CollectionBeans;
import com.apache.fastandroid.novel.find.bean.RecommandBeans;
import com.apache.fastandroid.novel.find.bean.RecommendBook;
import com.apache.fastandroid.novel.support.event.RefreshCollectionListEvent;
import com.apache.fastandroid.novel.support.sdk.NovelSdk;
import com.apache.fastandroid.novel.support.util.NovelLog;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.support.bean.RefreshConfig;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.fragment.ARecycleViewSwipeRefreshFragment;
import com.tesla.framework.ui.fragment.ATabsFragment;
import com.tesla.framework.ui.fragment.itemview.BaseItemViewCreator;
import com.tesla.framework.ui.fragment.itemview.IITemView;
import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by 01370340 on 2017/9/24.
 */

public class BookSelfFragment extends ARecycleViewSwipeRefreshFragment<RecommendBook,RecommandBeans,RecommendBook> implements ATabsFragment.ITabInitData {

    public static BookSelfFragment newFragment() {
        BookSelfFragment fragment = new BookSelfFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @ViewInject(idStr = "layout_add_favorite")
    Button layout_add_favorite;

    @Override
    public int inflateContentView() {
        return R.layout.layout_book_self;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        layout_add_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("MainFragment");
                if (fragment != null && fragment instanceof NovelHomeTabsFragment){
                    NovelHomeTabsFragment novelHomeTabsFragment = (NovelHomeTabsFragment) fragment;
                    novelHomeTabsFragment.selectFindFragment();
                }
            }
        });
    }

    @Override
    protected void setUpRefreshConfig(RefreshConfig refreshConfig) {
        super.setUpRefreshConfig(refreshConfig);
        //refreshConfig.disalbeFooterMore();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        NovelLog.e("BookSelfFragment onResume");

    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        NovelLog.e("BookSelfFragment onDestroyView");
        EventBus.getDefault().unregister(this);
    }

    @Override
    public IItemViewCreator<RecommendBook> configItemViewCreator() {
        return new BaseItemViewCreator<RecommendBook>(getActivity()) {
            @Override
            protected int inflateItemView(int viewType) {
                return R.layout.novel_item_recommand;
            }

            @Override
            public IITemView<RecommendBook> newItemView(View contentView, int viewType) {
                return new BookSelfItemView(getActivity(),contentView);
            }
        };
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

    @Override
    public void requestData(RefreshMode mode) {
        super.requestData(mode);
        if (mode == RefreshMode.refresh){
            mode = RefreshMode.reset;
        }
        new LoadDataTask(mode).execute();
    }

    @Override
    public void onTabRequestData() {
        if (mDataChanged){
            requestData(RefreshMode.refresh);
            mDataChanged = false;
        }
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
            return NovelSdk.getInstance().getCollection();
        }

        @Override
        protected void onSuccess(CollectionBeans collectionBeans) {
            super.onSuccess(collectionBeans);
        }
    }
    private boolean mDataChanged = false;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshCollectionList(RefreshCollectionListEvent event){
        mDataChanged = true;
        NovelLog.d("refreshCollectionList--------->set mDataChanged = true");

    }





}

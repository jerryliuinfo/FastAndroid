package com.apache.fastandroid.news;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.apache.fastandroid.news.bean.NewsBean;
import com.apache.fastandroid.news.bean.NewsBeans;
import com.apache.fastandroid.news.sdk.NewsSDK;
import com.apache.fastandroid.news.view.InformationItemView;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.support.paging.IPaging;
import com.tesla.framework.support.paging.index.CustomIndexPaging;
import com.tesla.framework.ui.fragment.ARecycleViewSwipeRefreshFragment;
import com.tesla.framework.ui.fragment.itemview.BaseItemViewCreator;
import com.tesla.framework.ui.fragment.itemview.IITemView;
import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;

import java.util.List;

/**
 * Created by 01370340 on 2017/9/3.
 */

public class InformationFragment extends ARecycleViewSwipeRefreshFragment<NewsBean,NewsBeans,NewsBean> {

    public static InformationFragment newFragment() {
        Bundle args = new Bundle();
        InformationFragment fragment = new InformationFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public IItemViewCreator configItemViewCreator() {
        return new BaseItemViewCreator<NewsBean>(getActivity()) {
            @Override
            protected int inflateItemView(int viewType) {
                return InformationItemView.LAY_RES;
            }

            @Override
            public IITemView newItemView(View contentView, int viewType) {
                return new InformationItemView(getActivity(),contentView);
            }
        };
    }


    protected int pageSize = 20;                     // 每页加载条数

    @Override
    protected IPaging<NewsBean, NewsBeans> newPaging() {
        CustomIndexPaging paging = new CustomIndexPaging();
        paging.setPageSize(pageSize);
        return paging;
    }

    @Override
    public void requestData(RefreshMode mode) {
        super.requestData(mode);
        if (mode == RefreshMode.refresh){
            mode = RefreshMode.reset;
        }
        new LoadNewsTask(mode).execute();
    }

    class LoadNewsTask extends APagingTask<Void,Void,NewsBeans>{

        public LoadNewsTask(RefreshMode mode) {
            super(mode);
        }

        @Override
        protected List<NewsBean> parseResult(NewsBeans newsBeans) {
            return newsBeans.list;
        }


        @Override
        protected NewsBeans workInBackground(RefreshMode mode, String previousPage, String nextPage, Void... params)
                throws TaskException {
            int offset = 0;
            if (!TextUtils.isEmpty(nextPage)){
                offset = Integer.parseInt(nextPage);
            }
            return NewsSDK.newInstance().getNewsList(null,offset,pageSize);
        }
    }

}

package com.apache.fastandroid.topic.site;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.apache.fastandroid.topic.site.bean.SiteBeans;
import com.apache.fastandroid.topic.site.bean.SiteItem;
import com.apache.fastandroid.topic.site.bean.Siteable;
import com.apache.fastandroid.topic.site.bean.SitesBean;
import com.apache.fastandroid.topic.site.bean.SitesItem;
import com.apache.fastandroid.topic.site.sdk.SiteSDK;
import com.apache.fastandroid.topic.site.view.SizeItemBodyView;
import com.apache.fastandroid.topic.site.view.SizeItemHeaderView;
import com.apache.fastandroid.topic.R;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.support.bean.RefreshConfig;
import com.tesla.framework.ui.fragment.ARecycleViewFragment;
import com.tesla.framework.ui.fragment.itemview.BaseItemViewCreator;
import com.tesla.framework.ui.fragment.itemview.IITemView;
import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 01370340 on 2017/9/12.
 */

public class SitesListFragment extends ARecycleViewFragment<Siteable,ArrayList<Siteable>,SitesBean.Site> {

    public static SitesListFragment newFragment() {
        Bundle args = new Bundle();
        SitesListFragment fragment = new SitesListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void setUpRefreshConfig(RefreshConfig refreshConfig) {
        super.setUpRefreshConfig(refreshConfig);
        refreshConfig.disalbeFooterMore();
    }

    @Override
    public IItemViewCreator<Siteable> configItemViewCreator() {
        return new BaseItemViewCreator<Siteable>(getActivity()) {
            @Override
            protected int inflateItemView(int viewType) {
                if (viewType == 100){
                    return R.layout.item_site_header;
                }else {
                    return R.layout.item_site_body;
                }
            }

            @Override
            public IITemView<Siteable> newItemView(View contentView, int viewType) {
                if (viewType == 100){
                    return new SizeItemHeaderView(getActivity(),contentView);
                }else {
                    return new SizeItemBodyView(getActivity(),contentView);
                }
            }
        };
    }

    @Override
    protected RecyclerView.LayoutManager configLayoutManager() {


        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position < getAdapterItems().size()){
                    return (getAdapterItems().get(position) instanceof SiteItem) ? 1 : 2;
                }
                return 1;
            }
        });
        return layoutManager;
    }

    @Override
    public void requestData(RefreshMode mode) {
        super.requestData(mode);
        if (mode == RefreshMode.refresh){
            mode = RefreshMode.reset;
        }
        new LoadSizeTask(mode).execute();
    }

    class LoadSizeTask extends APagingTask<Void,Void,ArrayList<Siteable>>{
        public LoadSizeTask(RefreshMode mode) {
            super(mode);
        }

        @Override
        protected List<Siteable> parseResult(ArrayList<Siteable> siteables) {
            return siteables;
        }

        @Override
        protected ArrayList<Siteable> workInBackground(RefreshMode mode, String previousPage, String nextPage, Void... params) throws TaskException {
            SiteBeans beans =  SiteSDK.newInstance().getSiteList();
            return convertData(beans.list);
        }
    }


    // 转换数据
    private ArrayList<Siteable> convertData(final List<SitesBean> sitesList) {
        ArrayList<Siteable> items = new ArrayList<>();
        for (SitesBean sites : sitesList) {

            items.add(new SitesItem(sites.getName()));

            for (SitesBean.Site site : sites.getSites()) {
                items.add(new SiteItem(site.getName(), site.getUrl(), site.getAvatar_url()));
            }

            if (sites.getSites().size() % 2 == 1) {
                items.add(new SiteItem("", "", ""));
            }
        }
        return items;
    }
}

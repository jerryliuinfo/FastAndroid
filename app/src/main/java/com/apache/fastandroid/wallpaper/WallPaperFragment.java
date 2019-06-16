package com.apache.fastandroid.wallpaper;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.apache.fastandroid.pic.view.WallpaperItemView;
import com.apache.fastandroid.topic.support.bean.WallpaperBean;
import com.apache.fastandroid.topic.support.bean.WallpaperBeans;
import com.apache.fastandroid.topic.support.sdk.Sdk;
import com.tesla.framework.common.util.dimen.DimensUtil;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.support.bean.RefreshConfig;
import com.tesla.framework.support.paging.IPaging;
import com.tesla.framework.support.paging.index.IndexPaging;
import com.tesla.framework.ui.fragment.ARecycleViewSwipeRefreshFragment;
import com.tesla.framework.ui.fragment.itemview.IITemView;
import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;
import java.util.List;

/**
 * Created by 01370340 on 2017/11/19.
 */

public class WallPaperFragment extends ARecycleViewSwipeRefreshFragment<WallpaperBean,WallpaperBeans,WallpaperBean> {

    public static WallPaperFragment newFragment() {
        Bundle args = new Bundle();
        WallPaperFragment fragment = new WallPaperFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected RecyclerView.LayoutManager configLayoutManager() {
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        return gridLayoutManager;
    }



    @Override
    protected void setUpRefreshConfig(RefreshConfig refreshConfig) {
        super.setUpRefreshConfig(refreshConfig);
        int padding = DimensUtil.dp2px(getActivity(), 4);
        //xml中item之间左右间隔是4dp 这里给RefreshView左右再加4dp 这样就8dp了
        getRefreshView().setPadding(padding, 0, padding, 0);
    }


    @Override
    public IItemViewCreator<WallpaperBean> configItemViewCreator() {
        return new WallpaperItemViewCreator();
    }

    class WallpaperItemViewCreator implements IItemViewCreator<WallpaperBean>{

        @Override
        public View newContentView(LayoutInflater inflater, ViewGroup parent, int viewType) {
            return inflater.inflate(WallpaperItemView.LAY_RES,parent,false);
        }

        @Override
        public IITemView<WallpaperBean> newItemView(View contentView, int viewType) {
            return new WallpaperItemView(getActivity(), contentView);
        }
    }
    @Override
    public void requestData(RefreshMode mode) {
        super.requestData(mode);
        if (mode == RefreshMode.refresh){
            mode = RefreshMode.reset;
        }
        new WallPaperTask(mode).execute();
    }

    @Override
    protected IPaging<WallpaperBean, WallpaperBeans> newPaging() {
        return new IndexPaging<>();
    }

    class WallPaperTask extends APagingTask<Void,Void,WallpaperBeans>{

        public WallPaperTask(RefreshMode mode) {
            super(mode);
        }

        @Override
        protected List<WallpaperBean> parseResult(WallpaperBeans wallpaperBeans) {
            return wallpaperBeans.getItem().getWallpaperList();
        }

        @Override
        protected WallpaperBeans workInBackground(RefreshMode mode, String previousPage, String nextPage, Void... params) throws TaskException {
            int page = 1;
            //index分页是没有下拉的
            if (!TextUtils.isEmpty(nextPage)){
//                page = StrUtil.toInt(nextPage);
            }
            WallpaperBeans beans = Sdk.newInstance().getWallpaperList(page);
            if(beans.getItem().getWallpaperList().size() == 0){
                beans.setEndPaging(true);
            }

            return beans;
        }
    }
}

package com.apache.fastandroid.novel.find.rank;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.apache.fastandroid.novel.find.bean.RankingList;
import com.apache.fastandroid.novel.find.rank.view.RankItemGroupView;
import com.apache.fastandroid.novel.find.rank.view.RankItemView;
import com.apache.fastandroid.novel.support.sdk.NovelSdk;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.support.bean.RefreshConfig;
import com.tesla.framework.ui.activity.FragmentArgs;
import com.tesla.framework.ui.activity.FragmentContainerActivity;
import com.tesla.framework.ui.fragment.ARecycleViewFragment;
import com.tesla.framework.ui.fragment.itemview.BaseItemViewCreator;
import com.tesla.framework.ui.fragment.itemview.IITemView;
import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 01370340 on 2017/9/24.
 * "排行榜"的Fragment
 */

public class TopRankListFragment extends ARecycleViewFragment<RankingList.MaleBean,ArrayList<RankingList.MaleBean>,RankingList.MaleBean> {

    public static void launch(Activity from) {
        FragmentArgs args =  new FragmentArgs();
        FragmentContainerActivity.launch(from,TopRankListFragment.class,args);
    }

    @Override
    protected void setUpRefreshConfig(RefreshConfig refreshConfig) {
        super.setUpRefreshConfig(refreshConfig);
        refreshConfig.disalbeFooterMore();
    }

    @Override
    public IItemViewCreator<RankingList.MaleBean> configItemViewCreator() {
        return new BaseItemViewCreator<RankingList.MaleBean>(getActivity()) {
            @Override
            protected int inflateItemView(int viewType) {
                if (viewType == RankItemView.TYPE_GROUP){
                    return RankItemGroupView.LAY_RES ;
                }else {
                    return RankItemView.LAY_RES;
                }
            }

            @Override
            public IITemView<RankingList.MaleBean> newItemView(View contentView, int viewType) {
                if (viewType == RankItemView.TYPE_GROUP){
                    return new RankItemGroupView(getContext(), contentView);
                }else {
                    return new RankItemView(getContext(), contentView);
                }
            }
        };
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        setToolbarTitle("排行榜");
    }


    @Override
    public void requestData(RefreshMode mode) {
        super.requestData(mode);
        new LoadRankListData(mode).execute();
    }

    class LoadRankListData extends APagingTask<Void,Void,ArrayList<RankingList.MaleBean>>{


        public LoadRankListData(RefreshMode mode) {
            super(mode);
        }

        @Override
        protected void onSuccess(ArrayList<RankingList.MaleBean> maleBeen) {
            super.onSuccess(maleBeen);
            setItems(maleBeen);
        }

        @Override
        protected List<RankingList.MaleBean> parseResult(ArrayList<RankingList.MaleBean> maleBeen) {
            return maleBeen;
        }

        @Override
        protected ArrayList<RankingList.MaleBean> workInBackground(RefreshMode mode, String previousPage, String nextPage, Void... params) throws TaskException {
            return NovelSdk.getInstance().getRankingList();
        }


    }


}

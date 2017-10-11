package com.apache.fastandroid.novel.find.rank.subrank;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

import com.apache.fastandroid.novel.R;
import com.apache.fastandroid.novel.find.bean.RankingList;
import com.tesla.framework.common.util.ResUtil;
import com.tesla.framework.support.bean.TabItem;
import com.tesla.framework.ui.activity.FragmentArgs;
import com.tesla.framework.ui.activity.FragmentContainerActivity;
import com.tesla.framework.ui.fragment.ATabsTabLayoutFragment;

import java.util.ArrayList;

/**
 * Created by 01370340 on 2017/10/10.
 */

public class SubRankTabsFragment extends ATabsTabLayoutFragment<TabItem> {

    private RankingList.MaleBean maleBean;
    public static void launch(Activity from, RankingList.MaleBean maleBean) {
        FragmentArgs args =  new FragmentArgs();
        args.add("bean",maleBean);
        FragmentContainerActivity.launch(from,SubRankTabsFragment.class,args);
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        if (savedInstanceSate == null){
            maleBean = (RankingList.MaleBean) getArguments().getSerializable("bean");
        }else {
            maleBean = (RankingList.MaleBean) savedInstanceSate.getSerializable("bean");
        }
        setToolbarTitle(maleBean.title);
    }

    @Override
    protected ArrayList<TabItem> generateTabs() {
        ArrayList<TabItem> tabItems = new ArrayList<>();
        tabItems.add(new TabItem("0",ResUtil.getString(R.string.novel_sub_rank_tabs_week)));
        tabItems.add(new TabItem("1",ResUtil.getString(R.string.novel_sub_rank_tabs_month)));
        tabItems.add(new TabItem("2",ResUtil.getString(R.string.novel_sub_rank_tabs_total)));
        return tabItems;
    }

    @Override
    public Fragment newFragment(TabItem tabItem) {
        String rankingId = "";
        if ("0".equals(tabItem.getType())){
            rankingId = maleBean._id;
        }else if ("1".equals(tabItem.getType())){
            rankingId = maleBean.monthRank;
        }else if ("2".equals(tabItem.getType())){
            rankingId = maleBean.totalRank;
        }
        return SubRankFragment.newFragment(rankingId);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("bean", maleBean);
    }
}

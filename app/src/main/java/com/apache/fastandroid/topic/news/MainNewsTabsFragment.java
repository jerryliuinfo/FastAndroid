package com.apache.fastandroid.topic.news;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.apache.fastandroid.topic.support.bean.NewsChannelTable;
import com.apache.fastandroid.topic.support.sdk.Sdk;
import com.tesla.framework.support.bean.TabItem;
import com.tesla.framework.ui.activity.FragmentArgs;
import com.tesla.framework.ui.activity.FragmentContainerActivity;
import com.tesla.framework.ui.fragment.ATabsTabLayoutFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 01370340 on 2017/11/23.
 */

public class MainNewsTabsFragment extends ATabsTabLayoutFragment<TabItem> {
    public static void launch(Activity from) {
        FragmentArgs args =  new FragmentArgs();
        FragmentContainerActivity.launch(from,MainNewsTabsFragment.class,args);
    }
    public static MainNewsTabsFragment newFragment() {
        Bundle args = new Bundle();
        MainNewsTabsFragment fragment = new MainNewsTabsFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    protected ArrayList<TabItem> generateTabs() {
        ArrayList<TabItem> tabItems = new ArrayList<>();

        List<NewsChannelTable> channelTables = Sdk.newInstance().loadNewsChannelsStatic();
        for (NewsChannelTable channelTable : channelTables) {
            tabItems.add(new TabItem(channelTable.getNewsChannelName(),channelTable));
        }

        return tabItems;
    }

    @Override
    public Fragment newFragment(TabItem tabItem) {
        return MainNewsFragment.newFragment((NewsChannelTable) tabItem.getTag());
    }
}

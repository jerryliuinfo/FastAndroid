package com.apache.fastandroid.topic;

import android.support.v4.app.Fragment;

import com.apache.fastandroid.topic.fragment.TopicFragment;
import com.tesla.framework.support.bean.TabItem;
import com.tesla.framework.ui.fragment.ATabsTabLayoutFragment;

import java.util.ArrayList;

/**
 * Created by 01370340 on 2017/9/2.
 */

public class MainTabsFragment extends ATabsTabLayoutFragment<TabItem> {

    public static MainTabsFragment newFragment(){
        return new MainTabsFragment();
    }

    @Override
    protected ArrayList<TabItem> generateTabs() {
        ArrayList<TabItem> tabItems = new ArrayList<>();
        tabItems.add(new TabItem("0", "Topics"));
        tabItems.add(new TabItem("1", "News"));
        tabItems.add(new TabItem("2", "Sites"));
        return tabItems;
    }

    @Override
    public Fragment newFragment(TabItem tabItem) {
        return TopicFragment.newInstance();
    }
}

package com.apache.fastandroid.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.apache.fastandroid.ui.TabFragment;
import com.tesla.framework.support.bean.TabItem;
import com.tesla.framework.ui.fragment.ATabsTabLayoutFragment;

import java.util.ArrayList;

/**
 * Created by jerryliu on 2017/4/9.
 */

public class TestTabFragment extends ATabsTabLayoutFragment<TabItem> {

    public static TestTabFragment newFragment() {
         Bundle args = new Bundle();
         TestTabFragment fragment = new TestTabFragment();
        fragment.setArguments(args);
        return fragment;
    }
    

    @Override
    protected ArrayList<TabItem> generateTabs() {
        ArrayList<TabItem> tabItems = new ArrayList<>();
        tabItems.add(new TabItem("0", "推荐"));
        tabItems.add(new TabItem("0", "热点"));
        tabItems.add(new TabItem("0", "新闻"));
        return tabItems;
    }

    @Override
    public Fragment newFragment(TabItem tabItem) {
        return TabFragment.newFragment(tabItem);
    }


}

package com.apache.fastandroid.ui.fragment.video;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.apache.fastandroid.R;
import com.tesla.framework.common.util.ResUtil;
import com.tesla.framework.support.bean.TabItem;
import com.tesla.framework.ui.fragment.ATabsTabLayoutFragment;

import java.util.ArrayList;

/**
 * Created by jerryliu on 2017/4/11.
 */

public class VideoTabsFragment extends ATabsTabLayoutFragment<TabItem> {

    public static VideoTabsFragment newFragment() {
        Bundle args = new Bundle();
        VideoTabsFragment fragment = new VideoTabsFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected ArrayList<TabItem> generateTabs() {
        ArrayList<TabItem> tabItems = new ArrayList<>();
        tabItems.add(new TabItem("0", ResUtil.getString(R.string.tab_video_hot)));
        tabItems.add(new TabItem("1", ResUtil.getString(R.string.tab_video_laungh)));
        tabItems.add(new TabItem("2", ResUtil.getString(R.string.tab_video_carton)));
        tabItems.add(new TabItem("3", ResUtil.getString(R.string.tab_video_variety)));
        return tabItems;
    }

    @Override
    public Fragment newFragment(TabItem tabItem) {
        return VideoFragment.newFragment(tabItem.getTitle());
    }


}

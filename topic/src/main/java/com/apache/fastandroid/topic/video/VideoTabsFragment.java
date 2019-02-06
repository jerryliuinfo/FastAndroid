package com.apache.fastandroid.topic.video;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.tesla.framework.support.bean.TabItem;
import com.tesla.framework.ui.fragment.ATabsTabLayoutFragment;

import java.util.ArrayList;

/**
 * Created by jerryliu on 2017/4/11.
 */
//@Route(path = RouterMap.TOPIC.VIDEOS_FRAGMENT)
public class VideoTabsFragment extends ATabsTabLayoutFragment<TabItem> {

    public static VideoTabsFragment newFragment() {
        Bundle args = new Bundle();
        VideoTabsFragment fragment = new VideoTabsFragment();
        fragment.setArguments(args);
        return fragment;
    }
//      <string name="tab_video_laungh">搞笑</string>
//    <string name="tab_video_carton">动漫</string>
//    <string name="tab_video_variety">综艺</string>
    @Override
    protected ArrayList<TabItem> generateTabs() {
        ArrayList<TabItem> tabItems = new ArrayList<>();
        tabItems.add(new TabItem("0", "热门"));
        tabItems.add(new TabItem("1", "搞笑"));
        tabItems.add(new TabItem("2", "动漫"));
        tabItems.add(new TabItem("3", "综艺"));
        return tabItems;
    }

    @Override
    public Fragment newFragment(TabItem tabItem) {
        return VideoFragment.newFragment(tabItem.getTitle());
    }


}

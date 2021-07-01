package com.apache.fastandroid.topic;

import com.apache.fastandroid.topic.news.MainNewsFragment;
import com.apache.fastandroid.topic.support.bean.NewsChannelTable;
import com.apache.fastandroid.topic.support.sdk.Sdk;
import com.tesla.framework.support.bean.ITabItem;
import com.tesla.framework.support.bean.TabItem;
import com.tesla.framework.ui.fragment.ATabsTabLayoutFragmentNew;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

/**
 * Created by Jerry on 2021/6/29.
 */
public class MainNewsTabsFragmentNew extends ATabsTabLayoutFragmentNew<TabItem> {

    public static MainNewsTabsFragmentNew newInstance(){
        return new MainNewsTabsFragmentNew();
    }



    @Override
    protected Fragment newFragment(int position, ITabItem tabItem) {
        return MainNewsFragment.newFragment((NewsChannelTable) tabItem.tag());

    }

    @Override
    protected ArrayList<ITabItem> generateTabs() {
        ArrayList<ITabItem> tabItems = new ArrayList<>();
        List<NewsChannelTable> channelTables = Sdk.newInstance().loadNewsChannelsStatic();
        for (NewsChannelTable channelTable : channelTables) {
            tabItems.add(new TabItem(channelTable.getNewsChannelName(),channelTable));
        }
        return tabItems;
    }


}

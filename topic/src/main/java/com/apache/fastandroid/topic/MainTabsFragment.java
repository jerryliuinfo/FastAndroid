package com.apache.fastandroid.topic;

import android.support.v4.app.Fragment;
import com.apache.fastandroid.topic.news.InformationFragment;
import com.apache.fastandroid.topic.site.SitesListFragment;
import com.tesla.framework.support.bean.TabItem;
import com.tesla.framework.ui.fragment.ATabsTabLayoutFragment;
import java.util.ArrayList;

/**
 * Created by 01370340 on 2017/9/2.
 */
//@Route(path = RouterMap.TOPIC.INFORMAGTION_FRAGMENT)
public class MainTabsFragment extends ATabsTabLayoutFragment<TabItem> {

    public static MainTabsFragment newFragment(){
        return new MainTabsFragment();
    }

    @Override
    protected ArrayList<TabItem> generateTabs() {
        ArrayList<TabItem> tabItems = new ArrayList<>();
        tabItems.add(new TabItem("0", "主题"));
        tabItems.add(new TabItem("1", "资讯"));
        tabItems.add(new TabItem("2", "站点"));
        return tabItems;
    }

    @Override
    public Fragment newFragment(TabItem tabItem) {
        if ("0".equals(tabItem.getType())){
            return TopicListFragment.newFragment();
        }else if ("1".equals(tabItem.getType())){
            return InformationFragment.newFragment();
        }else if ("2".equals(tabItem.getType())){
            return SitesListFragment.newFragment();
        }
        return null;
    }
}

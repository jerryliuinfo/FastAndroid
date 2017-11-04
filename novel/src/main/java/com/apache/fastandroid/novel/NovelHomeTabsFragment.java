package com.apache.fastandroid.novel;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.apache.fastandroid.novel.bookshelf.BookSelfFragment;
import com.apache.fastandroid.novel.find.FindFragment;
import com.tesla.framework.support.bean.TabItem;
import com.tesla.framework.ui.fragment.ATabsTabLayoutFragment;

import java.util.ArrayList;

/**
 * Created by 01370340 on 2017/9/24.
 */

public class NovelHomeTabsFragment extends ATabsTabLayoutFragment<TabItem> {

    public static NovelHomeTabsFragment newFragment() {
        Bundle args = new Bundle();
        NovelHomeTabsFragment fragment = new NovelHomeTabsFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected ArrayList<TabItem> generateTabs() {
        ArrayList<TabItem> tabs = new ArrayList<>();
        tabs.add(new TabItem("0","书架"));
        tabs.add(new TabItem("1","社区"));
        tabs.add(new TabItem("2","发现"));
        return tabs;
    }

    @Override
    public Fragment newFragment(TabItem tabItem) {
        if ("0".equals(tabItem.getType())){
            return BookSelfFragment.newFragment();
        }else if ("1".equals(tabItem.getType())){
            return FindFragment.newFragment("1");
        } else if ("2".equals(tabItem.getType())){
            return FindFragment.newFragment("2");
        }

        return null;
    }


    public void selectFindFragment(){
        getViewPage().setCurrentItem(2);
    }






}

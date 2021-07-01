package com.apache.fastandroid.pic;

import android.os.Bundle;

import com.apache.fastandroid.R;
import com.blankj.utilcode.util.ResourceUtils;
import com.tesla.framework.support.bean.ITabItem;
import com.tesla.framework.support.bean.TabItem;
import com.tesla.framework.ui.fragment.ATabsTabLayoutFragmentNew;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;

/**
 * Created by jerryliu on 2017/4/11.
 */

public class PicTabsFragment extends ATabsTabLayoutFragmentNew<TabItem> {
    public static PicTabsFragment newFragment() {
         Bundle args = new Bundle();
         PicTabsFragment fragment = new PicTabsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected Fragment newFragment(int position, ITabItem tabItem) {
        return PicFragment.newFragment(tabItem.title());
    }

    @Override
    protected ArrayList<ITabItem> generateTabs() {
        ArrayList<ITabItem> tabItems = new ArrayList<>();
        tabItems.add(new TabItem("0", ResourceUtils.getString(R.string.tab_pic_beanuty)));
        tabItems.add(new TabItem("1", ResourceUtils.getString(R.string.tab_pic_carton)));
        tabItems.add(new TabItem("2", ResourceUtils.getString(R.string.tab_pic_star)));
        tabItems.add(new TabItem("3", ResourceUtils.getString(R.string.tab_pic_car)));
        tabItems.add(new TabItem("4", ResourceUtils.getString(R.string.tab_pic_take_photo)));
        tabItems.add(new TabItem("5", ResourceUtils.getString(R.string.tab_pic_food)));
        return tabItems;
    }


}

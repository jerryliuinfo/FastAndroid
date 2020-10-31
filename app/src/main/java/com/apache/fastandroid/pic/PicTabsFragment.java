package com.apache.fastandroid.pic;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.apache.fastandroid.R;
import com.tesla.framework.common.util.ResUtil;
import com.tesla.framework.support.bean.TabItem;
import com.tesla.framework.ui.fragment.ATabsTabLayoutFragment;

import java.util.ArrayList;

/**
 * Created by jerryliu on 2017/4/11.
 */

public class PicTabsFragment extends ATabsTabLayoutFragment<TabItem> {
    public static PicTabsFragment newFragment() {
         Bundle args = new Bundle();
         PicTabsFragment fragment = new PicTabsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected ArrayList<TabItem> generateTabs() {
        ArrayList<TabItem> tabItems = new ArrayList<>();
        tabItems.add(new TabItem("0", ResUtil.getString(R.string.tab_pic_beanuty)));
        tabItems.add(new TabItem("1", ResUtil.getString(R.string.tab_pic_carton)));
        tabItems.add(new TabItem("2", ResUtil.getString(R.string.tab_pic_star)));
        tabItems.add(new TabItem("3", ResUtil.getString(R.string.tab_pic_car)));
        tabItems.add(new TabItem("4", ResUtil.getString(R.string.tab_pic_take_photo)));
        tabItems.add(new TabItem("5", ResUtil.getString(R.string.tab_pic_food)));
        return tabItems;
    }

    @Override
    public Fragment newFragment(TabItem tabItem) {
        return PicFragment.newFragment(tabItem.getTitle());
    }
}

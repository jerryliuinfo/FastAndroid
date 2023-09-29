package com.tesla.framework.ui.fragment.tab;


import com.tesla.framework.support.bean.ITabItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

/**
 * Created by Jerry on 2020/11/24.
 * 继承FragmentStatePagerAdapter， 而不是 FragmentPagerAdapter 的原因是
 * 给RecycleView重新设置FragmentPagerAdapter 时界面不会刷新
 */
public abstract class PageTabAdapter extends FragmentStatePagerAdapter {

    private List<ITabItem> tabItems;

    public PageTabAdapter(@NonNull FragmentManager fm, int behavior, List<ITabItem> tabItems) {
        super(fm, behavior);
        if (tabItems == null){
            tabItems = new ArrayList<>();
        }
        this.tabItems = tabItems;
    }

    public List<ITabItem> getTabItems() {
        return tabItems;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        ITabItem tabItem = tabItems.get(position);
        return getFragmentByPosition(position,tabItem);
    }

    @Override
    public int getCount() {
        return tabItems.size();
    }

    /**
     * 使用这个方式，让页面不缓存，能够在清除fragment的时候对其做了删除
     *
     * @param object
     * @return
     */
    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabItems.get(position).title();
    }

    public abstract Fragment getFragmentByPosition(int position,ITabItem tabItem);
}

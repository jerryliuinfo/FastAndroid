package com.tesla.framework.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.google.android.material.tabs.TabLayout;
import com.tesla.framework.R;
import com.tesla.framework.databinding.FragmentTablayoutBinding;
import com.tesla.framework.support.bean.ITabItem;
import com.tesla.framework.ui.fragment.adpater.PageTabAdapter;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

/**
 * 对TabLayout的封装
 */
public abstract class ATabsTabLayoutFragmentNew extends BaseLifecycleFragment<FragmentTablayoutBinding> {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ArrayList<ITabItem> tabItems;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_tablayout;
    }



    @Override
    public void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabLayout);

        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        setupViewPage();

    }

    private int selectedTabIndex;
    private void setupViewPage(){
        tabLayout.setupWithViewPager(viewPager);

        tabItems = generateTabs();
        PageTabAdapter pagerAdapter = new PageTabAdapter(getChildFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, tabItems) {

            @Override
            public Fragment getFragmentByPosition(int position, ITabItem tabItem) {
                return newFragment(position,tabItem);
            }
        };

        viewPager.setAdapter(pagerAdapter);
        //懒加载，其实还是会加载多一个,在fragment中去控制延迟加载
        viewPager.setOffscreenPageLimit(tabItems.size());
        viewPager.setCurrentItem(selectedTabIndex);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                ATabsTabLayoutFragmentNew.this.onPageSelected(position);
            }
        });
    }

    protected void onPageSelected(int position){
        selectedTabIndex = position;
    }

    protected abstract Fragment newFragment(int position,ITabItem tabItem);

    protected abstract ArrayList<ITabItem> generateTabs();


    public void setCurrentItem(int index){
        if (selectedTabIndex == index){
            return;
        }
        viewPager.setCurrentItem(index);
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public TabLayout getTabLayout() {
        return tabLayout;
    }

    public int getSelectedTabIndex() {
        return selectedTabIndex;
    }
}

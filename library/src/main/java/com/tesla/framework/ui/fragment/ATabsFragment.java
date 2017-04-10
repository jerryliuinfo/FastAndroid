package com.tesla.framework.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;

import com.tesla.framework.R;
import com.tesla.framework.common.util.ActivityHelper;
import com.tesla.framework.support.bean.TabItem;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.activity.BaseActivity;
import com.tesla.framework.ui.fragment.adpater.FragmentPagerAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jerryliu on 2017/4/9.
 */

public abstract class ATabsFragment<T extends TabItem> extends ABaseFragment implements ViewPager.OnPageChangeListener {
    public static final String TAG = ATabsFragment.class.getSimpleName();


    public static final String SET_INDEX = "org.aisen.android.ui.SET_INDEX";// 默认选择第几个

    @ViewInject(idStr = "viewPager")
    ViewPager mViewPage;

    private ArrayList<T> mTabs;

    private int mCurrentPosition;

    private HashMap<String,WeakReference<Fragment>> fragments;
    private boolean isRetainFragments = false;// 如果系统先调用onSaveInstanceState方法，说明添加的Fragments是需要保留的

    @Override
    public int inflateContentView() {
        return R.layout.comm_ui_tabs;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        if (savedInstanceSate != null){
            mCurrentPosition = savedInstanceSate.getInt("currentPosition", 0);
            mTabs = (ArrayList<T>) savedInstanceSate.getSerializable("mTabs");
        }else {
            mCurrentPosition = 0;
            mTabs = generateTabs();
        }
        initTabs(savedInstanceSate);
    }


    private void initTabs(Bundle savedInstanceSate){
        if (getActivity() == null){
            return;
        }else if (getActivity() instanceof BaseActivity){
            if (((BaseActivity)getActivity()).isDestory()){
                return;
            }
        }
        if (savedInstanceSate == null){
            if (getArguments() != null && getArguments().containsKey(SET_INDEX)){
                if (Integer.parseInt(SET_INDEX) < mTabs.size()){
                    mCurrentPosition = Integer.parseInt(SET_INDEX);
                }
            }else if (!TextUtils.isEmpty(configLastTabKey())){
                String lastReadTab = ActivityHelper.getShareData(getContext(), "pageLastTab"+configLastTabKey());
                if (!TextUtils.isEmpty(lastReadTab)){
                    for (int i = 0; i < mTabs.size(); i++){
                        TabItem tabItem = mTabs.get(i);
                        if (lastReadTab.equals(tabItem.getType())){
                            mCurrentPosition = i;
                            break;
                        }
                    }
                }
            }
        }
        fragments = new HashMap<>();
        if (savedInstanceSate == null){
            for (int i = 0; i < mTabs.size(); i++){
                Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag(makeFragmentName(i));
                if (fragment != null){

                    try {
                        getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        setupViewPage(savedInstanceSate);
    }

    FragmentPagerAdapter adapter;

    protected void setupViewPage(Bundle savedInstanceSate){
        adapter = new TabFragmentAdapter(getFragmentManager());
        getViewPage().setAdapter(adapter);
        getViewPage().setCurrentItem(mCurrentPosition);
        getViewPage().addOnPageChangeListener(this);

    }

    class TabFragmentAdapter extends FragmentPagerAdapter {


        public TabFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            WeakReference<Fragment> weakRefrence = fragments.get(makeFragmentName(position));

            Fragment fragment = null;
            if (weakRefrence == null || weakRefrence.get() == null){
                fragment = newFragment(mTabs.get(position));
                fragments.put(makeFragmentName(position), new WeakReference<>(fragment));
            }

            return fragment;
        }

        @Override
        protected String makeFragmentName(int position) {
            return ATabsFragment.this.makeFragmentName(position);
        }


        @Override
        public int getCount() {
            return mTabs.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return ATabsFragment.this.makeFragmentName(position);
        }
    }




    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        isRetainFragments = true;
        outState.putInt("currentPosition", mCurrentPosition);
        outState.putSerializable("mTabs", mTabs);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!isRetainFragments){
            destroyFragment();
        }
    }

    private void destroyFragment(){
        if (getActivity() != null){
            if (getActivity() instanceof BaseActivity){
                BaseActivity baseActivity = (BaseActivity) getActivity();
                if (baseActivity.isDestory()){
                    return;
                }
            }


            try {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                for (Map.Entry<String,WeakReference<Fragment>> entry: fragments.entrySet()){
                    if (entry.getValue() != null && entry.getValue().get() != null){
                        Fragment fragment = entry.getValue().get();
                        if (fragment != null){
                            transaction.remove(fragment);
                        }

                    }
                }
                transaction.commit();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }


    public Fragment getCurrentFragment() {
        if (getViewPage() == null || adapter == null || adapter.getCount() < mCurrentPosition)
            return null;

        WeakReference<Fragment> weakRefrence = fragments.get(makeFragmentName(mCurrentPosition));
        if (weakRefrence != null && weakRefrence.get() != null){
            return weakRefrence.get();
        }
        return null;
    }

    public Fragment getFragment(String tabTitle) {
        if (fragments == null || TextUtils.isEmpty(tabTitle))
            return null;

        for (int i = 0; i < mTabs.size(); i++) {
            if (tabTitle.equals(mTabs.get(i).getTitle())) {
                WeakReference<Fragment> weakRefrence = fragments.get(makeFragmentName(i));
                return weakRefrence.get();
            }
        }

        return null;
    }

    public Fragment getFragment(int index) {
        if (mTabs.size() > index) {
            WeakReference<Fragment> weakRefrence = fragments.get(makeFragmentName(index));
            return weakRefrence.get();
        }

        return null;
    }

    /**
     * 每个tab的title必须不一样
     * @return
     */
    protected abstract ArrayList<T> generateTabs();


    /**
     * 是否保留最后选中的标签的key，return null代表不选中
     * 如果需要保留 反悔tab的type字段值
     * @return
     */
    protected String configLastTabKey(){
        return null;
    }

    protected String makeFragmentName(int position){
        return mTabs.get(position).getTitle();
    }

    public ViewPager getViewPage() {
        return mViewPage;
    }



    public ArrayList<T> getTabs() {
        return mTabs;
    }


    public int getCurrentPosition() {
        return mCurrentPosition;
    }


    @Override
    public void onPageSelected(int position) {
        mCurrentPosition = position;
        if (!TextUtils.isEmpty(configLastTabKey())){
            ActivityHelper.putShareData(getContext(),"pageLastTab"+configLastTabKey(), mTabs.get(mCurrentPosition).getTitle());
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void setCurrentPosition(int mCurrentPosition) {
        this.mCurrentPosition = mCurrentPosition;
    }

    public abstract Fragment newFragment(T tabItem);


    // 这个接口用于多页面时，只有当前的页面才加载数据，其他不显示的页面暂缓加载
    // 当每次onPagerSelected的时候，再调用这个接口初始化数据
    public interface ITabInitData {
        void onTabRequestData();

    }

}

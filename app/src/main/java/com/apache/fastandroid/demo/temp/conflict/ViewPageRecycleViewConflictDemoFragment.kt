package com.apache.fastandroid.demo.temp.conflict

import androidx.fragment.app.Fragment
import com.apache.fastandroid.home.HomeFragment
import com.apache.fastandroid.jetpack.navigation.advance.home.AboutFragment
import com.google.android.material.tabs.TabLayout
import com.tesla.framework.support.bean.TabItem
import com.tesla.framework.ui.fragment.ATabsTabLayoutFragmentNew
import com.tesla.framework.ui.fragment.tab.MyFragmentStateAdapter

/**
 * Created by Jerry on 2022/5/27.
 */
class ViewPageRecycleViewConflictDemoFragment: ATabsTabLayoutFragmentNew() {
    override fun createTabAdapter(): MyFragmentStateAdapter {
        val tabs = mutableListOf(TabItem("0", "garden"), TabItem("1","list"))
        return DemoAdapter(tabs,this)
    }

    override fun onConfigureTab(tab: TabLayout.Tab, position: Int, tabItem: TabItem) {
        tab.text = tabItem.title
    }

    private class DemoAdapter(tabItems:List<TabItem>,fragment: Fragment) :MyFragmentStateAdapter(tabItems,fragment) {

        override fun newFragment(position: Int, tabItem: TabItem): Fragment {
            return when(position){
                0 -> HomeFragment.newInstance()
                else -> HomeFragment.newInstance()
            }
        }

    }
}
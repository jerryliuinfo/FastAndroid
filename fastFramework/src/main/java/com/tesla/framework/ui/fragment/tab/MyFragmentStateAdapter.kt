package com.tesla.framework.ui.fragment.tab

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tesla.framework.support.bean.TabItem

/**
 * Created by Jerry on 2022/3/15.
 */
abstract class MyFragmentStateAdapter(val tabs:List<TabItem>,fragment:Fragment):FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return tabs.size
    }

    override fun createFragment(position: Int): Fragment {
        return newFragment(position,tabs[position])
    }

    abstract fun newFragment(position: Int, tabItem: TabItem):Fragment

}
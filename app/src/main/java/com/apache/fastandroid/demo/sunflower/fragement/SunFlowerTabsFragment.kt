package com.apache.fastandroid.demo.sunflower.fragement

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.apache.fastandroid.demo.tablayout.MY_GARDEN_PAGE_INDEX
import com.apache.fastandroid.demo.tablayout.PLANT_LIST_PAGE_INDEX
import com.google.android.material.tabs.TabLayout
import com.tesla.framework.support.bean.TabItem
import com.tesla.framework.ui.fragment.ATabsTabLayoutFragmentNew
import com.tesla.framework.ui.fragment.tab.MyFragmentStateAdapter

/**
 * Created by Jerry on 2022/3/14.
 */
class SunFlowerTabsFragment :ATabsTabLayoutFragmentNew(){


    override fun createTabAdapter(): MyFragmentStateAdapter {
        val tabs = mutableListOf(TabItem("0", "garden"), TabItem("1","list"))
        return PlantingAdapter(tabs,this)
    }

    override fun onConfigureTab(tab: TabLayout.Tab, position: Int, tabItem: TabItem) {
        tab.text = getTabTitle(position)
    }


    class PlantingAdapter(tabs:List<TabItem>,fragment: Fragment) :MyFragmentStateAdapter(tabs,fragment) {

        override fun newFragment(position: Int, tabItem: TabItem): Fragment {
            return when(position){
                0 -> PlantListViewFragment()
                else -> PlantListViewFragment()
            }
        }

    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            MY_GARDEN_PAGE_INDEX -> "Garden"
            PLANT_LIST_PAGE_INDEX -> "PlantList"
            else -> null
        }
    }
}
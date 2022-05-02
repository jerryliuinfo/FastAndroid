package com.apache.fastandroid.demo.tablayout

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.apache.fastandroid.R
import com.apache.fastandroid.jetpack.navigation.advance.home.AboutFragment
import com.apache.fastandroid.jetpack.navigation.advance.home.TitleFragment
import com.google.android.material.tabs.TabLayout
import com.tesla.framework.support.bean.ITabItem
import com.tesla.framework.support.bean.TabItem
import com.tesla.framework.ui.fragment.ATabsTabLayoutFragmentNew
import com.tesla.framework.ui.fragment.tab.MyFragmentStateAdapter
import java.util.ArrayList

const val MY_GARDEN_PAGE_INDEX = 0
const val PLANT_LIST_PAGE_INDEX = 1
/**
 * Created by Jerry on 2022/3/14.
 */
class ArticleTabsFragment:ATabsTabLayoutFragmentNew() {
    override fun createTabAdapter(): MyFragmentStateAdapter {
        val tabs = mutableListOf(TabItem("0", "garden"), TabItem("1","list"))
        return ArticleAdapter(tabs,this)
    }



    override fun onConfigureTab(tab: TabLayout.Tab, position: Int, tabItem: TabItem) {
        tab.text = getTabTitle(position)
    }



    private fun getTabTitle(position: Int): String? {
        return when (position) {
            MY_GARDEN_PAGE_INDEX -> "Garden"
            PLANT_LIST_PAGE_INDEX -> "PlantList"
            else -> null
        }
    }

    private class ArticleAdapter(tabItems:List<TabItem>,fragment: Fragment) :MyFragmentStateAdapter(tabItems,fragment) {

        override fun newFragment(position: Int, tabItem: TabItem): Fragment {
            return when(position){
                0 -> TitleFragment()
                else -> AboutFragment()
            }
        }

    }
}
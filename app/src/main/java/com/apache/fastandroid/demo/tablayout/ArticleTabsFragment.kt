package com.apache.fastandroid.demo.tablayout

import androidx.fragment.app.Fragment
import com.apache.fastandroid.jetpack.navigation.advance.home.AboutFragment
import com.apache.fastandroid.jetpack.navigation.advance.home.TitleFragment
import com.tesla.framework.support.bean.TabItem
import com.tesla.framework.ui.fragment.tab.ATabsTabLayoutFragment
import com.tesla.framework.ui.fragment.tab.CustomFragmentStateAdapter

const val MY_GARDEN_PAGE_INDEX = 0
const val PLANT_LIST_PAGE_INDEX = 1
/**
 * Created by Jerry on 2022/3/14.
 */
class ArticleTabsFragment: ATabsTabLayoutFragment() {


    override fun createTabAdapter(): CustomFragmentStateAdapter {
        return ArticleAdapter(tabItems(),this)
    }

    override fun tabItems(): List<TabItem> {
        val tabs = mutableListOf(TabItem("0", "garden"), TabItem("1","list"))
        return tabs
    }





    private fun getTabTitle(position: Int): String? {
        return when (position) {
            MY_GARDEN_PAGE_INDEX -> "Garden"
            PLANT_LIST_PAGE_INDEX -> "PlantList"
            else -> null
        }
    }

    private class ArticleAdapter(tabItems:List<TabItem>,fragment: Fragment) :CustomFragmentStateAdapter(tabItems,fragment) {

        override fun newFragment(position: Int, tabItem: TabItem): Fragment {
            return when(position){
                0 -> TitleFragment()
                else -> AboutFragment()
            }
        }

    }
}
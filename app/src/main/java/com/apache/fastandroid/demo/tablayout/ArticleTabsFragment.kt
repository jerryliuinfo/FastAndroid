package com.apache.fastandroid.demo.tablayout

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.apache.fastandroid.R
import com.apache.fastandroid.jetpack.navigation.advance.home.AboutFragment
import com.apache.fastandroid.jetpack.navigation.advance.home.TitleFragment
import com.google.android.material.tabs.TabLayout
import com.tesla.framework.support.bean.ITabItem
import com.tesla.framework.ui.fragment.ATabsTabLayoutFragmentNew
import java.util.ArrayList

const val MY_GARDEN_PAGE_INDEX = 0
const val PLANT_LIST_PAGE_INDEX = 1
/**
 * Created by Jerry on 2022/3/14.
 */
class ArticleTabsFragment:ATabsTabLayoutFragmentNew() {
    override fun createTabAdapter(): FragmentStateAdapter {
        return ArticleAdapter(this)
    }

    override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
        tab.text = getTabTitle(position)
    }



    private fun getTabTitle(position: Int): String? {
        return when (position) {
            MY_GARDEN_PAGE_INDEX -> "Garden"
            PLANT_LIST_PAGE_INDEX -> "PlantList"
            else -> null
        }
    }
//
//
//    override fun generateTabs(): ArrayList<ITabItem> {
//    }

    class ArticleAdapter(fragment: Fragment) :FragmentStateAdapter(fragment) {
        private val tabFragmentsCreator  = mapOf(
            0 to { TitleFragment() },
            1 to { AboutFragment() }
        )
        override fun getItemCount(): Int {
           return tabFragmentsCreator.size
        }

        override fun createFragment(position: Int): Fragment {
            return tabFragmentsCreator[position]?.invoke() ?: throw IndexOutOfBoundsException()
        }

    }
}
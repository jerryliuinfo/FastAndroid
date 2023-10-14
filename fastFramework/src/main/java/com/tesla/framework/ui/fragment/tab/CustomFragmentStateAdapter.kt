package com.tesla.framework.ui.fragment.tab

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tesla.framework.support.bean.TabItem
import com.tesla.framework.ui.fragment.base.RefreshableFragment

/**
 * Created by Jerry on 2022/3/15.
 */
abstract class CustomFragmentStateAdapter(val tabs:List<TabItem>, private val fragment:Fragment):FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return tabs.size
    }

    override fun createFragment(position: Int): Fragment {
        return newFragment(position,tabs[position])
    }

    abstract fun newFragment(position: Int, tabItem: TabItem):Fragment

    fun getFragment(position: Int):Fragment?{
        return fragment.activity?.supportFragmentManager?.findFragmentByTag("f" + getItemId(position))
    }


    fun refreshContent() {
        for (i in 0 until tabs.size) {
            val fragment = getFragment(i)
            if (fragment != null && fragment is RefreshableFragment) {
                (fragment as RefreshableFragment).refreshContent()
            }
        }
    }
}
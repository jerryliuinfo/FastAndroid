package com.tesla.framework.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.tesla.framework.databinding.FragmentTablayoutBinding
import com.tesla.framework.support.bean.ITabItem

/**
 * 对TabLayout的封装
 */
abstract class ATabsTabLayoutFragmentNew : BaseVBFragment<FragmentTablayoutBinding>(FragmentTablayoutBinding::inflate) {

    private var tabItems: ArrayList<ITabItem>? = null


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        setupViewPage()
    }

    abstract fun createTabAdapter(): FragmentStateAdapter

    var selectedTabIndex = 0
        private set

    private fun setupViewPage() {
      /*  viewBinding.tabLayout.apply {
            tabMode = TabLayout.MODE_FIXED
            setupWithViewPager(viewBinding.viewpager)
        }*/
       /* val pagerAdapter: PageTabAdapter = object : PageTabAdapter(
            childFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
            tabItems
        ) {
            override fun getFragmentByPosition(position: Int, tabItem: ITabItem): Fragment {
                return newFragment(position, tabItem)
            }
        }*/

        viewBinding.viewpager.apply {
            adapter = createTabAdapter()
        }

        TabLayoutMediator(viewBinding.tabLayout, viewBinding.viewpager) { tab, position ->
            onConfigureTab(tab,position)
        }.attach()



        /*viewBinding.viewpager.apply {
            adapter = pagerAdapter
            offscreenPageLimit = tabItems?.size ?:1
            currentItem = selectedTabIndex
            addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {

                }

                override fun onPageSelected(position: Int) {
                    this@ATabsTabLayoutFragmentNew.onPageSelected(position)
                }

                override fun onPageScrollStateChanged(state: Int) {
                }

            })

        }*/


    }

    abstract fun onConfigureTab(tab: TabLayout.Tab, position: Int)

    protected fun onPageSelected(position: Int) {
        selectedTabIndex = position
    }

//    protected abstract fun newFragment(position: Int, tabItem: ITabItem?): Fragment
//    protected abstract fun generateTabs(): ArrayList<ITabItem>

    fun setCurrentItem(index: Int) {
        if (selectedTabIndex == index) {
            return
        }
        viewBinding.viewpager.currentItem = index
    }
}
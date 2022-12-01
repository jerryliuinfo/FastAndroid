package com.tesla.framework.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.tesla.framework.databinding.FragmentTablayoutBinding
import com.tesla.framework.support.bean.TabItem
import com.tesla.framework.ui.fragment.tab.MyFragmentStateAdapter

/**
 * 对TabLayout的封装
 */
abstract class ATabsTabLayoutFragmentNew : BaseBindingFragment<FragmentTablayoutBinding>(FragmentTablayoutBinding::inflate) {

    private lateinit var mAdapter:MyFragmentStateAdapter


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        setupViewPage()
    }

    abstract fun createTabAdapter(): MyFragmentStateAdapter


    var selectedTabIndex = 0
        private set

    private fun setupViewPage() {
        mAdapter  = createTabAdapter()
        mBinding.viewpager.apply {
            adapter = mAdapter
        }

        TabLayoutMediator(mBinding.tabLayout, mBinding.viewpager) { tab, position ->
            onConfigureTab(tab,position,mAdapter.tabs[position])
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

    abstract fun onConfigureTab(tab: TabLayout.Tab, position: Int,tabItem:TabItem)

    protected fun onPageSelected(position: Int) {
        selectedTabIndex = position
    }

//    protected abstract fun newFragment(position: Int, tabItem: ITabItem?): Fragment
//    protected abstract fun generateTabs(): ArrayList<ITabItem>

    fun setCurrentItem(index: Int) {
        if (selectedTabIndex == index) {
            return
        }
        mBinding.viewpager.currentItem = index
    }
}
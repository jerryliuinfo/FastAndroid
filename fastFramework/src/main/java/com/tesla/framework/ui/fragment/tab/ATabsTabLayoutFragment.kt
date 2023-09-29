package com.tesla.framework.ui.fragment.tab

import android.os.Bundle
import android.view.LayoutInflater
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.tesla.framework.databinding.FragmentTablayoutBinding
import com.tesla.framework.support.bean.TabItem
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * 对TabLayout的封装
 */
abstract class ATabsTabLayoutFragment : BaseBindingFragment<FragmentTablayoutBinding>
    (FragmentTablayoutBinding::inflate) {

    private lateinit var mAdapter:MyFragmentStateAdapter

    //只公开 get 不公开 set
    var selectedTabIndex = 0
        private set



    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        setupViewPage()
    }

    abstract fun createTabAdapter(): MyFragmentStateAdapter



    private fun setupViewPage() {
        TabLayoutMediator(mBinding.tabLayout, mBinding.viewpager) { tab, position ->
            onConfigureTab(tab,position,mAdapter.tabs[position])
        }.attach()

        mBinding.viewpager.apply {
            adapter = createTabAdapter().also {
                mAdapter = it
            }
            offscreenPageLimit = tabItems().size
            currentItem = selectedTabIndex

//            registerOnPageChangeCallback(this@ATabsTabLayoutFragment)


           /* addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {

                }

                override fun onPageSelected(position: Int) {
                    this@ATabsTabLayoutFragment.onPageSelected(position)
                }

                override fun onPageScrollStateChanged(state: Int) {
                }

            })*/

        }

    }

    abstract fun tabItems():List<TabItem>

     fun onConfigureTab(tab: TabLayout.Tab, position: Int,tabItem:TabItem){
         tab.text = tabItems()[position].title
     }


    fun setCurrentItem(index: Int) {
        if (selectedTabIndex == index) {
            return
        }
        mBinding.viewpager.currentItem = index
    }
}
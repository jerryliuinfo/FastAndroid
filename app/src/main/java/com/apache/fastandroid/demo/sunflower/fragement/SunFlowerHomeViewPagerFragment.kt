package com.apache.fastandroid.demo.sunflower.fragement

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentViewPagerBinding
import com.apache.fastandroid.demo.sunflower.adapter.SunFlowerPagerAdapter
import com.apache.fastandroid.demo.tablayout.MY_GARDEN_PAGE_INDEX
import com.apache.fastandroid.demo.tablayout.PLANT_LIST_PAGE_INDEX
import com.google.android.material.tabs.TabLayoutMediator
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2022/3/14.
 * done
 */
class SunFlowerHomeViewPagerFragment :BaseBindingFragment<FragmentViewPagerBinding>(FragmentViewPagerBinding::inflate){

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        mBinding.viewPager.adapter = SunFlowerPagerAdapter(this)
        TabLayoutMediator(mBinding.tabs,mBinding.viewPager){ tab,position ->
            tab.setIcon(getTabIcon(position))
            tab.text = (getTabTitle(position))
        }.attach()
        //
        (activity as AppCompatActivity).setSupportActionBar(mBinding.toolbar)
    }

    private fun getTabIcon(position: Int): Int {
        return when (position) {
            MY_GARDEN_PAGE_INDEX -> R.drawable.garden_tab_selector
            PLANT_LIST_PAGE_INDEX -> R.drawable.plant_list_tab_selector
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            MY_GARDEN_PAGE_INDEX -> "MyGarden"
            PLANT_LIST_PAGE_INDEX -> "PlantList"
            else -> null
        }
    }


}
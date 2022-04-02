package com.apache.fastandroid.demo.sunflower.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.apache.fastandroid.demo.sunflower.fragement.GardenFragment
import com.apache.fastandroid.demo.sunflower.fragement.PlantListFragment
import java.lang.IndexOutOfBoundsException

/**
 * Created by Jerry on 2022/3/23.
 */
const val MY_GARDEN_PAGE_INDEX = 0
const val PLANT_LIST_PAGE_INDEX = 1

class SunFlowerPagerAdapter(fragment:Fragment):FragmentStateAdapter(fragment) {
    private val tabFragmentCreators:Map<Int, () ->Fragment> = mapOf(
        MY_GARDEN_PAGE_INDEX to { GardenFragment() },
        PLANT_LIST_PAGE_INDEX to { PlantListFragment() }
    )

    override fun getItemCount(): Int {
        return tabFragmentCreators.size
    }

    override fun createFragment(position: Int): Fragment {
        return tabFragmentCreators[position]?.invoke()?:throw IndexOutOfBoundsException()
    }
}
package com.apache.fastandroid.demo.sunflower.fragement

import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewpager2.widget.ViewPager2
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentGardenBinding
import com.apache.fastandroid.demo.sunflower.adapter.PLANT_LIST_PAGE_INDEX
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2022/3/23.
 */
class GardenFragment:BaseVBFragment<FragmentGardenBinding>(FragmentGardenBinding::inflate) {
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.addPlant.setOnClickListener {
            requireActivity().findViewById<ViewPager2>(R.id.view_pager).currentItem = PLANT_LIST_PAGE_INDEX
        }

    }
}
package com.apache.fastandroid.demo.sample.listentry

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentRecycleviewBinding
import com.apache.fastandroid.demo.bean.MainItem
import com.apache.fastandroid.component.compress.CompressDemoFragment
import com.apache.fastandroid.demo.keybordvisibility.KeybordVisibilityDemoActivity
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2023/7/11.
 */
class ListEntryDemoFragment :
    BaseBindingFragment<FragmentRecycleviewBinding>(FragmentRecycleviewBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        val adapter = MainAdapter(getScenarios(requireContext())).apply {
            onMainItemClickListener = { startActivity(it.intent) }
        }
        mBinding.recyclerView.adapter = adapter
    }


    fun getScenarios(context: Context): List<MainItem> =
        listOf(
            MainItem(
                R.string.large_data_scenario_title,
                R.string.large_data_scenario_description,
                CompressDemoFragment.getNavigationIntent(context)
            ),
            MainItem(
                R.string.non_bridge_large_data_scenario_title,
                R.string.non_bridge_large_data_scenario_description,
                KeybordVisibilityDemoActivity.getNavigationIntent(
                    context,
                )
            ),
        )

}
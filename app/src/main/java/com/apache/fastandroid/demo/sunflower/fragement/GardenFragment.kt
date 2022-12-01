package com.apache.fastandroid.demo.sunflower.fragement

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentGardenBinding
import com.apache.fastandroid.demo.sunflower.adapter.GardenPlantingAdapter
import com.apache.fastandroid.demo.sunflower.adapter.PLANT_LIST_PAGE_INDEX
import com.apache.fastandroid.demo.sunflower.db.SunFlowDatabase
import com.apache.fastandroid.demo.sunflower.repository.GardenPlantingRepository
import com.apache.fastandroid.demo.sunflower.viewmodel.GardenPlantingListViewModel
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2022/3/23.
 */
class GardenFragment:BaseBindingFragment<FragmentGardenBinding>(FragmentGardenBinding::inflate) {

    private val viewModel:GardenPlantingListViewModel by viewModels{ GardenPlantingListViewModel.GardenPlantingViewModelFactory(
        GardenPlantingRepository.getInstance(SunFlowDatabase.getInstance(requireContext()).gardenPlantingDao())
    ) }


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.addPlant.setOnClickListener {
            requireActivity().findViewById<ViewPager2>(R.id.view_pager).currentItem = PLANT_LIST_PAGE_INDEX
        }

        val adapter = GardenPlantingAdapter(requireActivity(),viewModel)
        mBinding.recycleview.adapter = adapter
        subscribeUI(adapter)
    }

    private fun subscribeUI(adapter: GardenPlantingAdapter){
        viewModel.plantAndGardenPlantings.observe(viewLifecycleOwner){ plants ->
            mBinding.hasPlantings = !plants.isNullOrEmpty()
            adapter.submitList(plants)
        }
    }
}
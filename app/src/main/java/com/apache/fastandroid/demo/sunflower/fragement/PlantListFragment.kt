package com.apache.fastandroid.demo.sunflower.fragement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentPlantListBinding
import com.apache.fastandroid.demo.sunflower.adapter.PlantAdapterNew
import com.apache.fastandroid.demo.sunflower.db.SunFlowDatabase
import com.apache.fastandroid.demo.sunflower.repository.PlantRepository
import com.apache.fastandroid.demo.sunflower.viewmodel.PlantListViewModel
import com.apache.fastandroid.demo.sunflower.viewmodel.PlantListViewModelFactory
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2022/3/14.
 */
class PlantListFragment:BaseBindingFragment<FragmentPlantListBinding>(FragmentPlantListBinding::inflate) {

    private val mViewModel:PlantListViewModel by viewModels{
        val platnDao = SunFlowDatabase.getInstance(requireContext()).plantDao()
        PlantListViewModelFactory(PlantRepository.getInstance(platnDao), SavedStateHandle())
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        setHasOptionsMenu(true)

        val adapter = PlantAdapterNew(requireActivity()).apply {
            setHasStableIds(true)
        }
        mBinding.plantList.adapter = adapter


        subscribeUI(adapter)

    }

    private fun subscribeUI(adapter:PlantAdapterNew){
        mViewModel.plants.observe(this){
            adapter.submitList(it)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_plant_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.filter_zone ->{
                updateData()
                true
            }else -> super.onOptionsItemSelected(item)
        }

    }

    private fun updateData(){
        with(mViewModel){
            if (isFiltered()){
                clearGrowZonNumber()
            }else{
                setGrowZonNumber(9)
            }
        }
    }


}
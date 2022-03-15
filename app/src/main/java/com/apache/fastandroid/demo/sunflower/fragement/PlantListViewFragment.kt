package com.apache.fastandroid.demo.sunflower.fragement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.recyclerview.widget.GridLayoutManager
import com.apache.fastandroid.R
import com.apache.fastandroid.demo.sunflower.bean.Plant
import com.apache.fastandroid.demo.sunflower.db.SunFlowDatabase
import com.apache.fastandroid.demo.sunflower.repository.PlantRepository
import com.apache.fastandroid.demo.sunflower.viewmodel.PlantListViewModel
import com.apache.fastandroid.demo.sunflower.viewmodel.PlantListViewModelFactory
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tesla.framework.component.imageloader.ImageLoaderManager
import com.tesla.framework.databinding.CommUiRecycleviewNewBinding
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2022/3/14.
 */
class PlantListViewFragment:BaseVBFragment<CommUiRecycleviewNewBinding>(CommUiRecycleviewNewBinding::inflate) {

    private val mViewModel:PlantListViewModel by viewModels{
        val platnDao = SunFlowDatabase.getInstance(requireContext()).plantDao()
        PlantListViewModelFactory(PlantRepository.getInstance(platnDao), SavedStateHandle())
    }

    private lateinit var mAdapter:PlantAdapter
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        setHasOptionsMenu(true)
        viewBinding.recycleview.apply {
            mAdapter = PlantAdapter().apply {
                layoutManager = GridLayoutManager(requireContext(),2)
            }
            adapter = mAdapter
        }

        mViewModel.plants.observe(this){
            mAdapter.setNewData(it)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_plant_list,menu)

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

    class PlantAdapter:BaseQuickAdapter<Plant,BaseViewHolder>(R.layout.list_item_plant){
        override fun convert(helper: BaseViewHolder, item: Plant) {
            val imageView = helper.getView<ImageView>(R.id.plant_item_image)
            ImageLoaderManager.getInstance().showImage(imageView,item.imageUrl)
            helper.setText(R.id.plant_item_title, item.name)
        }

    }
}
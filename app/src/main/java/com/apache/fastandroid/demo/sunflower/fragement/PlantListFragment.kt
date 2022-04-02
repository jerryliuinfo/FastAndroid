package com.apache.fastandroid.demo.sunflower.fragement

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.recyclerview.widget.GridLayoutManager
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentPlantListBinding
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
class PlantListFragment:BaseVBFragment<FragmentPlantListBinding>(FragmentPlantListBinding::inflate) {

    private val mViewModel:PlantListViewModel by viewModels{
        val platnDao = SunFlowDatabase.getInstance(requireContext()).plantDao()
        PlantListViewModelFactory(PlantRepository.getInstance(platnDao), SavedStateHandle())
    }

    override fun bindUI(rootView: View?) {
        super.bindUI(rootView)
        //需要加上这个，onCreateOptionsMenu 才会生效
        setHasOptionsMenu(true)
    }
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        val adapter = PlantAdapter()
        mBinding.plantList.adapter = adapter


        subscribeUI(adapter)

    }

    private fun subscribeUI(adapter:PlantAdapter){
        mViewModel.plants.observe(this){
            adapter.setNewData(it)
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

    class PlantAdapter:BaseQuickAdapter<Plant,BaseViewHolder>(R.layout.list_item_plant){
        override fun convert(helper: BaseViewHolder, item: Plant) {
            val imageView = helper.getView<ImageView>(R.id.plant_item_image)
            ImageLoaderManager.getInstance().showImage(imageView,item.imageUrl)
            helper.setText(R.id.plant_item_title, item.name)
        }

    }
}
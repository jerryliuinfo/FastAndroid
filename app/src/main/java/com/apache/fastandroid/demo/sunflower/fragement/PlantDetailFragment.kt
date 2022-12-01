package com.apache.fastandroid.demo.sunflower.fragement

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ShareCompat
import androidx.lifecycle.ViewModelProvider
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentPlantDetailBinding
import com.apache.fastandroid.demo.sunflower.bean.Plant
import com.apache.fastandroid.demo.sunflower.db.SunFlowDatabase
import com.apache.fastandroid.demo.sunflower.repository.GardenPlantingRepository
import com.apache.fastandroid.demo.sunflower.repository.PlantRepository
import com.apache.fastandroid.demo.sunflower.viewmodel.PlantDetailViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.tesla.framework.ui.fragment.BaseDataBindingFragment

/**
 * Created by Jerry on 2022/4/3.
 */
class PlantDetailFragment:BaseDataBindingFragment<FragmentPlantDetailBinding>(FragmentPlantDetailBinding::inflate) {

    private lateinit var plantDetailViewModel: PlantDetailViewModel


    override fun initViewModel() {
        super.initViewModel()

        val plantId = arguments?.getString("plantId")
        val plantDao = SunFlowDatabase.getInstance(requireContext()).plantDao()
        val gardenPlantingDao = SunFlowDatabase.getInstance(requireContext()).gardenPlantingDao()


        val factory = PlantDetailViewModel.provideFactory(PlantRepository.getInstance(plantDao),
            GardenPlantingRepository.getInstance(gardenPlantingDao), plantId?:"")

        plantDetailViewModel = ViewModelProvider(this, factory).get(PlantDetailViewModel::class.java)
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
        setHasOptionsMenu(true)
        mBinding.apply {
            viewModel = plantDetailViewModel
            lifecycleOwner = viewLifecycleOwner
            callback = Callback{ plant ->
                hideAppBarFab(mBinding.fab)
                plantDetailViewModel.addPlantToGarden()
                Snackbar.make(root, R.string.added_plant_to_garden, Snackbar.LENGTH_LONG)
                    .show()
            }

            toolbar.setNavigationOnClickListener {
                requireActivity().finish()
            }
            toolbar.setOnMenuItemClickListener {  item ->
                when(item.itemId){
                    R.id.action_share -> {
                        createShareIntent()
                        true
                    }
                    else -> false
                }
            }
        }

    }

    // Helper function for calling a share functionality.
    // Should be used when user presses a share button/menu item.
    @Suppress("DEPRECATION")
    private fun createShareIntent() {
        val shareText = plantDetailViewModel.plant.value.let { plant ->
            if (plant == null) {
                ""
            } else {
                getString(R.string.share_text_plant, plant.name)
            }
        }
        val shareIntent = ShareCompat.IntentBuilder.from(requireActivity())
            .setText(shareText)
            .setType("text/plain")
            .createChooserIntent()
            .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        startActivity(shareIntent)
    }

    private fun hideAppBarFab(fab:FloatingActionButton){
        val params = fab.layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior as FloatingActionButton.Behavior
        behavior.isAutoHideEnabled = false
        fab.hide()
    }

    fun interface Callback{
        fun add(plant:Plant)
    }
}
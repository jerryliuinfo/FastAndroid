package com.apache.fastandroid.demo.sunflower.fragement

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import com.tesla.framework.ui.activity.BaseBindingActivity

/**
 * Created by Jerry on 2024/6/10.
 */
class PlantDetailActivity:BaseBindingActivity<FragmentPlantDetailBinding>() {

    private lateinit var plantDetailViewModel: PlantDetailViewModel

    companion object{
        fun launch(context:Context, plantId:String){
            context.startActivity(Intent(context,PlantDetailActivity::class.java).apply {
                putExtra("plantId",plantId)
            })
        }
    }



    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)

        val plantId = intent.getStringExtra("plantId")

        val plantDao = SunFlowDatabase.getInstance(this).plantDao()
        val gardenPlantingDao = SunFlowDatabase.getInstance(this).gardenPlantingDao()


        val factory = PlantDetailViewModel.provideFactory(
            PlantRepository.getInstance(plantDao),
            GardenPlantingRepository.getInstance(gardenPlantingDao), plantId?:"")

        plantDetailViewModel = ViewModelProvider(this, factory).get(PlantDetailViewModel::class.java)



        // setHasOptionsMenu(true)
        mBinding.apply {
            viewModel = plantDetailViewModel
            lifecycleOwner = this@PlantDetailActivity
            callback = Callback { plant ->
                hideAppBarFab(mBinding.fab)
                plantDetailViewModel.addPlantToGarden()
                Snackbar.make(root, R.string.added_plant_to_garden, Snackbar.LENGTH_LONG)
                    .show()
            }

            toolbar.setNavigationOnClickListener {
                this@PlantDetailActivity.finish()
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
        val shareIntent = ShareCompat.IntentBuilder.from(this)
            .setText(shareText)
            .setType("text/plain")
            .createChooserIntent()
            .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        startActivity(shareIntent)
    }

    private fun hideAppBarFab(fab: FloatingActionButton){
        val params = fab.layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior as FloatingActionButton.Behavior
        behavior.isAutoHideEnabled = false
        fab.hide()
    }

    fun interface Callback{
        fun add(plant: Plant)
    }

}
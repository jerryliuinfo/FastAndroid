package com.apache.fastandroid.demo.sunflower.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.apache.fastandroid.BuildConfig
import com.apache.fastandroid.demo.sunflower.repository.GardenPlantingRepository
import com.apache.fastandroid.demo.sunflower.repository.PlantRepository
import com.apache.fastandroid.jetpack.BaseViewModel
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

/**
 * Created by Jerry on 2022/4/3.
 */
class PlantDetailViewModel(private val plantRepository: PlantRepository,private val gardenPlantingRepository: GardenPlantingRepository,
                           private val plantId:String):BaseViewModel() {

    val isPlanted = gardenPlantingRepository.isPlanted(plantId).asLiveData()
    val plant = plantRepository.getPlant(plantId).asLiveData()

    fun addPlantToGarden() {
        viewModelScope.launch {
            gardenPlantingRepository.createGradenPlanting(plantId)
        }
    }

//    fun hasValidUnsplashKey() = (BuildConfig.UNSPLASH_ACCESS_KEY != "null")
    fun hasValidUnsplashKey() = true

    companion object{
        fun provideFactory(plantRepository: PlantRepository,
                           gardenPlantingRepository: GardenPlantingRepository,
                           plantId:String):ViewModelProvider.Factory{

            return PlantDetailViewModelFactory(plantRepository,gardenPlantingRepository,plantId)
        }
    }

    class PlantDetailViewModelFactory(private val plantRepository: PlantRepository,private val gardenPlantingRepository: GardenPlantingRepository,
                                      private val plantId:String): ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PlantDetailViewModel::class.java)){
                return PlantDetailViewModel(plantRepository,gardenPlantingRepository,plantId) as T
            }else{
                throw IllegalArgumentException("Unknown ViewModel class")
            }

        }
    }
}
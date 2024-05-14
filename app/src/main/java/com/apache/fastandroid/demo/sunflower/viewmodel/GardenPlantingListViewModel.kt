package com.apache.fastandroid.demo.sunflower.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.apache.fastandroid.demo.sunflower.bean.GardenPlanting
import com.apache.fastandroid.demo.sunflower.bean.PlantAndGardenPlantings
import com.apache.fastandroid.demo.sunflower.repository.GardenPlantingRepository
import com.apache.fastandroid.jetpack.BaseViewModel
import kotlinx.coroutines.launch

/**
 * Created by Jerry on 2022/4/3.
 */
class GardenPlantingListViewModel(private val gardenPlantingRepository: GardenPlantingRepository):BaseViewModel() {

     val plantAndGardenPlantings:LiveData<List<PlantAndGardenPlantings>> = gardenPlantingRepository.getPlantedGardens().asLiveData()

     fun removeGardenPlanting(plantId: String){
          viewModelScope.launch {
               gardenPlantingRepository.removeGardenPlanting(GardenPlanting(plantId))
          }
     }


     class GardenPlantingViewModelFactory(private val repository: GardenPlantingRepository):ViewModelProvider.Factory{
          override fun <T : ViewModel> create(modelClass: Class<T>): T {
               return GardenPlantingListViewModel(repository) as T
          }

     }
}
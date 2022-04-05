package com.apache.fastandroid.demo.sunflower.viewmodel

import androidx.lifecycle.*
import com.apache.fastandroid.demo.sunflower.bean.GardenPlanting
import com.apache.fastandroid.demo.sunflower.bean.PlantAndGardenPlantings
import com.apache.fastandroid.demo.sunflower.repository.GardenPlantingRepository
import com.apache.fastandroid.jetpack.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

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
          override fun <T : ViewModel?> create(modelClass: Class<T>): T {
               return GardenPlantingListViewModel(repository) as T
          }

     }
}
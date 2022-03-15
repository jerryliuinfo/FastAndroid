package com.apache.fastandroid.demo.sunflower.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.apache.fastandroid.demo.sunflower.repository.PlantRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

/**
 * Created by Jerry on 2022/3/14.
 */
class PlantListViewModel(repository: PlantRepository, private val savedStateHandle: SavedStateHandle):ViewModel() {

    companion object {
        private const val NO_GROW_ZONE = -1
        private const val GROW_ZONE_SAVED_STATE_KEY = "GROW_ZONE_SAVED_STATE_KEY"
    }

    private val growZone: MutableStateFlow<Int> = MutableStateFlow(
        savedStateHandle.get(GROW_ZONE_SAVED_STATE_KEY) ?: NO_GROW_ZONE
    )

    val plants = growZone.flatMapLatest { zone ->
        if (zone == NO_GROW_ZONE){
            repository.getPlants()
        }else{
            repository.getPlantsWithGrowZoneNumber(zone)
        }
    }.asLiveData()



    fun setGrowZonNumber(number: Int){
        growZone.value = number
    }

    fun clearGrowZonNumber(){
        growZone.value = NO_GROW_ZONE
    }

    fun isFiltered():Boolean = growZone.value != NO_GROW_ZONE

    init {
        viewModelScope.launch {
            growZone.collect { newGrowZone ->
                savedStateHandle.set(GROW_ZONE_SAVED_STATE_KEY,newGrowZone)
            }
        }

    }
}
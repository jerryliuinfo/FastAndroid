package com.apache.fastandroid.demo.sunflower.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.apache.fastandroid.demo.sunflower.repository.PlantRepository

/**
 * Created by Jerry on 2022/3/14.
 */
class PlantListViewModelFactory(private val repository: PlantRepository, private val savedStateHandle: SavedStateHandle): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PlantListViewModel(repository, savedStateHandle) as T
    }
}
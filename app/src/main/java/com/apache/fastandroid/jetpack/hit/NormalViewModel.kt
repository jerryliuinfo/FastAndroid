package com.apache.fastandroid.jetpack.hit

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.apache.fastandroid.demo.sunflower.repository.PlantRepository
import com.apache.fastandroid.demo.sunflower.viewmodel.PlantListViewModel
import com.apache.fastandroid.home.HomeReporsitoryKt
import com.apache.fastandroid.home.db.HomeDao
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Jerry on 2022/3/20.
 */
class NormalViewModel (private val repository: Repository) : ViewModel() {

    fun doWork() {
        repository.doRepositoryWork()
    }


    class NormalViewModelFactory(private val repository: Repository): ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return NormalViewModel(repository) as T
        }
    }
}
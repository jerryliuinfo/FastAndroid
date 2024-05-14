package com.apache.fastandroid.jetpack.hit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by Jerry on 2022/3/20.
 */
class NormalViewModel (private val repository: Repository) : ViewModel() {

    fun doWork() {
        repository.doRepositoryWork()
    }


    class NormalViewModelFactory(private val repository: Repository): ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NormalViewModel(repository) as T
        }
    }
}
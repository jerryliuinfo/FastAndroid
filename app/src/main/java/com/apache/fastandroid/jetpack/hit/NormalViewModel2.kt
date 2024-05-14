package com.apache.fastandroid.jetpack.hit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.apache.fastandroid.jetpack.Repository2

/**
 * Created by Jerry on 2022/3/20.
 */
class NormalViewModel2 (private val repository: Repository2) : ViewModel() {

    fun doWork() {
        repository.doRepositoryWork()
    }


    class NormalViewModel2Factory(private val repository: Repository2): ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NormalViewModel2(repository) as T
        }
    }
}
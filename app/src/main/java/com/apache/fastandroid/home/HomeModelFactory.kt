package com.apache.fastandroid.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HomeModelFactory(private val repository: HomeReporsitoryKt) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(repository) as T
    }
}
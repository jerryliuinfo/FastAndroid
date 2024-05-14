package com.apache.fastandroid.demo.paging.poster.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.apache.fastandroid.network.api.ApiService

class PosterViewModelFactory(private val apiService: ApiService) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PosterViewModel::class.java)) {
            return PosterViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
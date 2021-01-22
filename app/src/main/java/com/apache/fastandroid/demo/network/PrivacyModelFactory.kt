package com.apache.fastandroid.demo.network

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.apache.fastandroid.demo.network.reporsity.PrivacyReporsity

class PrivacyModelFactory(private val repository: PrivacyReporsity) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PrivacyViewModelNew(repository) as T
    }
}
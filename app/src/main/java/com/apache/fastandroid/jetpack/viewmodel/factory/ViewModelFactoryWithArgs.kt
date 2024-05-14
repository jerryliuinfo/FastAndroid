package com.apache.fastandroid.jetpack.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.apache.fastandroid.jetpack.reporsity.UserReporsity
import com.apache.fastandroid.jetpack.viewmodel.ViewModelWithArgs

/**
 * Created by Jerry on 2021/2/8.
 */
class ViewModelFactoryWithArgs(private val reporsity: UserReporsity): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ViewModelWithArgs(reporsity) as T
    }
}
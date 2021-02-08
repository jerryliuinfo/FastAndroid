package com.apache.fastandroid.jetpack.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.apache.fastandroid.demo.databinding.UserViewModel
import com.apache.fastandroid.jetpack.reporsity.UserReporsity

/**
 * Created by Jerry on 2021/2/8.
 */
class UserModelFactory(private val reporsity: UserReporsity): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserInfoViewModel(reporsity) as T
    }
}
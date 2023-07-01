package com.apache.fastandroid.jetpack.hit.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Jerry on 2022/3/20.
 */
class HitViewModel @ViewModelInject constructor() : ViewModel() {

    fun doWork() {
        println("HitViewModel doWork")
    }

}
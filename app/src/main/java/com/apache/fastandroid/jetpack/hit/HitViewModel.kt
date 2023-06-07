package com.apache.fastandroid.jetpack.hit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Jerry on 2022/3/20.
 */
@HiltViewModel
class HitViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    fun doWork() {
        repository.doRepositoryWork()


        viewModelScope.launch {

        }
    }

}
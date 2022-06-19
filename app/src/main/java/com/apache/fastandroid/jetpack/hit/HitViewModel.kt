package com.apache.fastandroid.jetpack.hit

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Jerry on 2022/3/20.
 */
@HiltViewModel
class HitViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    fun doWork() {
        repository.doRepositoryWork()
    }

}
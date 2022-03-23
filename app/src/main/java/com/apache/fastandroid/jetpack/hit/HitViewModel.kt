package com.apache.fastandroid.jetpack.hit

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.apache.fastandroid.home.HomeReporsitoryKt
import com.apache.fastandroid.home.db.HomeDao
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Jerry on 2022/3/20.
 */
class HitViewModel @ViewModelInject constructor(private val repository: Repository) : ViewModel() {

    fun doWork() {
        repository.doRepositoryWork()
    }

}
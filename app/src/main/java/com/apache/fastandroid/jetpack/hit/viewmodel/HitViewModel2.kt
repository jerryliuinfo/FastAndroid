package com.apache.fastandroid.jetpack.hit.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apache.fastandroid.jetpack.hit.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Jerry on 2022/3/20.
 * 注意，@ViewModelInjdect 的方式已经不能使用了,
 * 参考：https://developer.android.com/training/dependency-injection/hilt-jetpack?hl=zh-cn
 *
 */
@HiltViewModel
class HitViewModel2 @Inject constructor(val repository: Repository) : ViewModel() {

    fun doWork() {
        repository.doRepositoryWork()
    }

}
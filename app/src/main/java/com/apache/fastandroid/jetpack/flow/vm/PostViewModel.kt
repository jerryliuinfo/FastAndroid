package com.apache.fastandroid.jetpack.flow.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apache.fastandroid.jetpack.flow.api.ApiHelper
import com.apache.fastandroid.jetpack.flow.ui_state.PostsUiState
import com.apache.fastandroid.network.model.Resource
import com.tesla.framework.component.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Jerry on 2022/6/19.
 */
class PostViewModel(private val apiHelper: ApiHelper):ViewModel(){
    private val _uiState = MutableStateFlow<PostsUiState>(PostsUiState.Success(emptyList()))
    //将不可变的 StateFlow 进行公开
    val uiState:StateFlow<PostsUiState> = _uiState

    init {
        loadData()
    }

    private var fetchJob:Job ?= null

    fun loadData(){
        Logger.d("P]ostViewModel init")
        _uiState.value = PostsUiState.Loading()

        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            apiHelper.getUsers()
                .catch { e->
                    _uiState.value = PostsUiState.Error(e)
            }.collect{
                _uiState.value = PostsUiState.Success(it)
            }
        }
    }


}
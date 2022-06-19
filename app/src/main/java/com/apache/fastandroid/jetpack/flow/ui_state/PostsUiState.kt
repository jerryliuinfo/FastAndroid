package com.apache.fastandroid.jetpack.flow.ui_state
import com.apache.fastandroid.network.model.ApiUser

sealed class PostsUiState{
    data class Loading(val nothing: Nothing ?= null): PostsUiState()
    data class Success(val postList: List<ApiUser> ): PostsUiState()
    data class Error(val exception: Throwable): PostsUiState()
    data class ShowToast(val id: Int): PostsUiState()
}


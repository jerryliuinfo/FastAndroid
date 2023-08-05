package com.apache.fastandroid.demo.paging.poster.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.apache.fastandroid.demo.paging.poster.datasource.PostDataSource
import com.apache.fastandroid.network.api.ApiService
import com.apache.fastandroid.network.model.ArticleApi
import kotlinx.coroutines.flow.Flow

class PosterViewModel(private val apiService: ApiService) : ViewModel() {

    val listData: Flow<PagingData<ArticleApi>> = Pager(PagingConfig(pageSize = 6)) {
        PostDataSource(apiService)
    }.flow.cachedIn(viewModelScope)
}
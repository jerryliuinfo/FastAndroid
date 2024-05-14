package com.apache.fastandroid.demo.showcase

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.apache.fastandroid.demo.showcase.albumlist.AlbumListViewModel
import com.apache.fastandroid.demo.showcase.domain.usecase.GetAlbumListUseCase

/**
 * Created by Jerry on 2022/3/14.
 */
class AlbumListViewModelFactory(private val getAlbumListUseCase: GetAlbumListUseCase, private val savedStateHandle: SavedStateHandle): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AlbumListViewModel(savedStateHandle, getAlbumListUseCase) as T
    }
}
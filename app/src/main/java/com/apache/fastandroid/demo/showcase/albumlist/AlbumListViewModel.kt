package com.apache.fastandroid.demo.showcase.albumlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.apache.fastandroid.demo.showcase.domain.model.Album
import com.apache.fastandroid.demo.showcase.domain.usecase.GetAlbumListUseCase
import com.tesla.framework.component.showcase.BaseAction
import com.tesla.framework.component.showcase.BaseState
import com.tesla.framework.component.showcase.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Created by Jerry on 2023/5/14.
 */
internal class AlbumListViewModel(
    private val state: SavedStateHandle,
    private val usecase: GetAlbumListUseCase,
) : BaseViewModel<AlbumListViewModel.UiState, AlbumListViewModel.Action>(UiState.Loading) {

    companion object {
        const val DEFAULT_QUERY_NAME = "Jackson"
        private const val SAVED_QUERY_KEY = "query"
    }

    fun loadData(query: String? = (state.get(SAVED_QUERY_KEY) as? String) ?: DEFAULT_QUERY_NAME) {
        getAlbumList(query)
    }

    private var job: Job? = null

    private fun getAlbumList(query: String?) {
        if (job != null) {
            job?.cancel()
            job = null
        }

        state[SAVED_QUERY_KEY] = query

        job = viewModelScope.launch {

            try {
                usecase(query).also { result ->
                    val action = when (result) {
                        is com.apache.fastandroid.network.model.result.Result.Success -> {
                            if (result.value.isEmpty()) {
                                Action.AlbumListLoadFailure
                            } else {
                                Action.AlbumListLoadSuccess(result.value)
                            }
                        }
                        is com.apache.fastandroid.network.model.result.Result.Failure -> {
                            Action.AlbumListLoadFailure
                        }
                    }
                    sendAction(action)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }


        }
    }



    internal sealed interface Action : BaseAction<UiState> {
        class AlbumListLoadSuccess(private val albums: List<Album>) : Action {
            override fun reduce(state: UiState): UiState = UiState.Content(albums)
        }

        object AlbumListLoadFailure : Action {
            override fun reduce(state: UiState) = UiState.Error
        }
    }

    internal sealed interface UiState : BaseState {
        data class Content(val albums: List<Album>) : UiState
        object Loading : UiState
        object Error : UiState
    }
}

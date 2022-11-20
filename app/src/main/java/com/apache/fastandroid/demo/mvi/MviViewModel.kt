package com.apache.fastandroid.demo.mvi

import androidx.lifecycle.*
import com.apache.fastandroid.home.HomeReporsitoryKt
import com.apache.fastandroid.home.toArticle
import com.apache.fastandroid.util.extensitons.FetchStatus
import com.apache.fastandroid.util.extensitons.PageState
import com.apache.fastandroid.util.extensitons.asLiveData
import com.tesla.framework.component.mvicore.LiveEvents
import com.tesla.framework.component.mvicore.setEvent
import com.tesla.framework.component.mvicore.setState
import kotlinx.coroutines.launch

/**
 * Created by Jerry on 2022/4/19.
 */
class MviViewModel(val repository: HomeReporsitoryKt):ViewModel() {

    private var count: Int = 0

    private val _viewStates: MutableLiveData<MainViewState> = MutableLiveData(MainViewState())
    val viewStates = _viewStates.asLiveData()

    private val _viewEvents: LiveEvents<MainViewEvent> = LiveEvents() //一次性的事件，与页面状态分开管理
    val viewEvents = _viewEvents.asLiveData()

    fun dispatch(viewAction: MainViewAction) {
        when (viewAction) {
            MainViewAction.FabClicked -> fabClicked()
            MainViewAction.OnSwipeRefresh -> fetchNews()
            MainViewAction.FetchNews -> fetchNews()
        }
    }



    private fun fabClicked() {
        count++
        _viewEvents.setEvent(MainViewEvent.ShowToast(message = "Fab clicked count $count"))
    }

    private fun fetchNews() {
        _viewStates.setState {
            copy(fetchStatus = FetchStatus.Fetching)
        }
        viewModelScope.launch {
            when (val result = repository.loadTopArticleCoByPageStatus()) {
                is PageState.Error -> {
                    _viewStates.setState {
                        copy(fetchStatus = FetchStatus.Fetched)
                    }
                    _viewEvents.setEvent(MainViewEvent.ShowToast(message = result.message))
                }
                is PageState.Success -> {
                    _viewStates.setState {
                        copy(fetchStatus = FetchStatus.Fetched, newsList = result.data.map { it.toArticle() })
                    }
                }
            }
        }
    }

    class MviModelFactory(private val repository: HomeReporsitoryKt) : ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MviViewModel(repository) as T
        }
    }
}
package com.apache.fastandroid.demo.mvi

import com.apache.fastandroid.network.model.Article
import com.apache.fastandroid.network.model.ArticleApi
import com.apache.fastandroid.util.extensitons.FetchStatus


data class MainViewState(
    val fetchStatus: FetchStatus = FetchStatus.NotFetched,
    val newsList: List<Article> = emptyList()
)

sealed class MainViewEvent {
    data class ShowSnackbar(val message: String) : MainViewEvent()
    data class ShowToast(val message: String) : MainViewEvent()
}

sealed class MainViewAction {
//    data class NewsItemClicked(val newsItem: NewsItem) : MainViewAction()
    object FabClicked : MainViewAction()
    object OnSwipeRefresh : MainViewAction()
    object FetchNews : MainViewAction()
}


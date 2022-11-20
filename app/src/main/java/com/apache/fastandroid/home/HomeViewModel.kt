package com.apache.fastandroid.home

import androidx.lifecycle.*
import com.apache.fastandroid.bean.PageInfo
import com.apache.fastandroid.jetpack.BaseViewModel
import com.apache.fastandroid.network.model.Article
import com.apache.fastandroid.network.model.ArticleApi
import com.apache.fastandroid.network.model.HomeArticleResponse
import com.tesla.framework.component.logger.Logger
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * Created by Jerry on 2022/2/23.
 */
class HomeViewModel(
    private val reporsitoryKt: HomeReporsitoryKt,
    private val savedStateHandle: SavedStateHandle?
) : BaseViewModel() {


    private val _articleList = MutableLiveData<Result<List<Article>>>()


    val pageInfo = PageInfo()

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }

    val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading
    private val _forceUpdate = MutableLiveData(false)

    val articleList: LiveData<Result<List<Article>>> = _forceUpdate.switchMap { forceUpdate ->
        if (forceUpdate) {
            pageInfo.reset()
            onRefreshData()
        }
        _articleList
    }


    fun refresh() {
        _forceUpdate.postValue(true)
    }

    init {
        loadData(true)
    }

    fun loadData(forceUpdate: Boolean) {
        _forceUpdate.postValue(true)
    }

    fun loadAuthorInfo(authorId: String) {
        Logger.d("loadAuthorInfo authorId:$authorId")
    }

    suspend fun loadHotData(): HomeArticleResponse {
        return reporsitoryKt.loadHomeArticleCo(pageInfo.page)
    }


    suspend fun loadTopArticle(): List<ArticleApi> {
        return reporsitoryKt.loadTopArticleCo()
    }

    fun onRefreshData() {

        pageInfo.reset()
        viewModelScope.launch {
            _dataLoading.value = true
            try {
                val articles: MutableList<ArticleApi> = arrayListOf()

                val topData = async { loadTopArticle() }

                topData.await()?.let {
                    articles.addAll(it)
                }
                val hotData = async { loadHotData() }
                hotData.await().datas?.let {
                    articles.addAll(it)
                }

                val articleList: List<Article> = articles.map {
                    convertToArticle(it)
                }

                _articleList.postValue(Result.success(articleList))
            } catch (e: Exception) {
                e.printStackTrace()
                _articleList.postValue(Result.failure(e))
            } finally {
                _dataLoading.value = false

            }

        }
    }

    fun loadMore() {

        launch({
            loadHotData().datas?.let { apiList ->
                val articleList: List<Article> = apiList.map {
                    convertToArticle(it)
                }

                _articleList.postValue(Result.success(articleList))
            }

        }, {

        })
    }


    private fun convertToArticle(api: ArticleApi): Article {
        return api.let {
            Article(
                author = it.author,
                shareUser = it.shareUser,
                chapterName = it.chapterName,
                link = it.link,
                title = it.title,
                collect = it.collect,
                superChapterName = it.superChapterName,
                niceDate = it.niceDate,
                fresh = it.fresh,
                top = it.top,
                loadAuthorInfo = {
                    loadAuthorInfo(api.author)
                }

            )
        }
    }

}

fun ArticleApi.toArticle(): Article {
    return this.let {
        Article(
            author = it.author,
            shareUser = it.shareUser,
            chapterName = it.chapterName,
            link = it.link,
            title = it.title,
            collect = it.collect,
            superChapterName = it.superChapterName,
            niceDate = it.niceDate,
            fresh = it.fresh,
            top = it.top,

            )
    }
}
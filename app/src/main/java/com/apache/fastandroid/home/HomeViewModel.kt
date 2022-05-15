package com.apache.fastandroid.home

import androidx.lifecycle.*
import com.apache.fastandroid.bean.PageInfo
import com.apache.fastandroid.jetpack.BaseViewModel
import com.apache.fastandroid.network.model.Article
import com.apache.fastandroid.network.model.HomeArticleResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * Created by Jerry on 2022/2/23.
 */
class HomeViewModel(
    private val reporsitoryKt: HomeReporsitoryKt,
    private val savedStateHandle: SavedStateHandle?
) : BaseViewModel() {


    private val _articleList = MutableLiveData<List<Article>>()
//    val articleList: LiveData<List<Article>> = _articleList


    val pageInfo = PageInfo()

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading
    private val _forceUpdate = MutableLiveData(false)

    val articleList: LiveData<List<Article>> = _forceUpdate.switchMap { forceUpdate ->
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


    suspend fun loadHotData(): HomeArticleResponse {
        return reporsitoryKt.loadHomeArticleCo(pageInfo.page)
    }


    suspend fun loadTopArticle(): List<Article> {
        return reporsitoryKt.loadTopArticleCo()
    }

    fun onRefreshData() {

        pageInfo.reset()
        viewModelScope.launch {
            _dataLoading.value = true
            val hotData = async { loadHotData() }
            val topData = async { loadTopArticle() }

            val articles: MutableList<Article> = arrayListOf()

            topData.await()?.let {
                articles.addAll(it)
            }
            hotData.await().datas?.let {
                articles.addAll(it)
            }
            _articleList.postValue(articles)
            _dataLoading.value = false
        }
    }

    fun loadMore() {

        launch({
            loadHotData().datas?.let {
                _articleList.postValue(it)
            }

        }, {

        })
    }


}
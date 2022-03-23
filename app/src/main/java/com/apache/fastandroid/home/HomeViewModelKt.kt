package com.apache.fastandroid.home

import androidx.lifecycle.*
import com.apache.fastandroid.jetpack.BaseViewModel
import com.apache.fastandroid.network.model.Article
import com.apache.fastandroid.network.model.HomeArticleResponse
import com.tesla.framework.common.util.log.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Jerry on 2022/2/23.
 */
class HomeViewModelKt(val reporsitoryKt: HomeReporsitoryKt):BaseViewModel() {
    private val _topArticleLiveData = MutableLiveData<List<Article>>()

    private val _homeArticleLiveData = MutableLiveData<HomeArticleResponse>()

    val topArticleLiveData:LiveData<List<Article>>
        get() = _topArticleLiveData

    val homeArticleLiveData:LiveData<HomeArticleResponse>
        get() = _homeArticleLiveData


    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun loadHomeArticleCo(pageNum: Int){
        launch({
            var articles = reporsitoryKt.loadHomeArticleCo(pageNum)
            _homeArticleLiveData.value = articles
        },{
            it.printStackTrace()
        })
    }

    fun loadHomeData(pageNum: Int) {
        loadHomeArticleCo(pageNum)
        if (pageNum == 0){
           loadTopArticle()
        }

    }


    fun loadTopArticle(){
        launch({
            var topArticleCo = reporsitoryKt.loadTopArticleCo()
            _topArticleLiveData.value = topArticleCo ?: emptyList()
        },{
            _topArticleLiveData.value = emptyList()

        })
    }

    override fun onCleared() {
        super.onCleared()
        com.tesla.framework.component.logger.Logger.d("onCleared cancel viewModel")
    }




}
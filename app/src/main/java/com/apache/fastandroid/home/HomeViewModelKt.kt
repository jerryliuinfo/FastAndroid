package com.apache.fastandroid.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apache.fastandroid.jetpack.BaseViewModel
import com.apache.fastandroid.network.model.Article
import com.apache.fastandroid.network.model.HomeArticleResponse
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
            launch({
                var topArticleCo = reporsitoryKt.loadTopArticleCo()
                _topArticleLiveData.value = topArticleCo ?: emptyList()
            },{
                _topArticleLiveData.value = emptyList()

            })
        }

    }


}
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

    private val _topArticleLiveData : MutableLiveData<List<Article>> by lazy {
        MutableLiveData<List<Article>>().also {
            loadHomeData(1)
        }
    }
    val topArticleLiveData:LiveData<List<Article>> = _topArticleLiveData


    private val _homeArticleLiveData = MutableLiveData<HomeArticleResponse>()


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
            _homeArticleLiveData.value = HomeArticleResponse(0, emptyList(),0,true,0,10,20)
        })
    }

    fun loadHomeData(pageNum: Int) {
        loadHomeArticleCo(pageNum)
        if (pageNum == 0){
           loadTopArticle()
        }

    }


    fun loadTopArticle(){
        println("loadTopArticle ---> ")
        launch({
            var topArticleCo = reporsitoryKt.loadTopArticleCo()
            _topArticleLiveData.value = topArticleCo ?: emptyList()
        },{
            _topArticleLiveData.value = emptyList()

        })
    }

    override fun onCleared() {
        super.onCleared()
    }




}
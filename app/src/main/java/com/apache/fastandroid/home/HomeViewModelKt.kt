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
class HomeViewModelKt(val reporsitoryKt: HomeReporsitoryKt):BaseViewModel() {

    private val _topArticleLiveData : MutableLiveData<List<Article>> by lazy {
        MutableLiveData<List<Article>>().also {
//            onRefresh()
        }
    }
    val topArticleLiveData:LiveData<List<Article>> = _topArticleLiveData


    private val _refreshing = MutableLiveData<Boolean>()
    val refreshing: LiveData<Boolean> = _refreshing





    private val _homeArticleLiveData = MutableLiveData<HomeArticleResponse>()

    val homeArticleLiveData:LiveData<HomeArticleResponse> = _homeArticleLiveData

    val pageInfo = PageInfo()

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun loadHomeArticleCo(pageNum: Int){
        launch({
            var articles = reporsitoryKt.loadHomeArticleCo(pageNum)
            _homeArticleLiveData.value = articles!!
        },{
            it.printStackTrace()
            _homeArticleLiveData.value = HomeArticleResponse(0, emptyList(),0,true,0,10,20)
        })

    }



    suspend fun loadHotData(): HomeArticleResponse{
        return reporsitoryKt.loadHomeArticleCo(pageInfo.page)
    }


    suspend fun loadTopArticle():List<Article>{
       return reporsitoryKt.loadTopArticleCo()
    }


    fun onRefresh() {
        pageInfo.reset()
        _refreshing.value = true

        viewModelScope.launch {
            val hotData =  async { loadHotData()}
            val topData =  async { loadTopArticle() }
            _homeArticleLiveData.value = hotData.await()
            _topArticleLiveData.value = topData.await()

            if (_refreshing.value == true){
                _refreshing.value = false
            }
        }
    }
    fun loadMore(){
        viewModelScope.launch {
            _homeArticleLiveData.value = loadHotData()!!
        }
    }


}
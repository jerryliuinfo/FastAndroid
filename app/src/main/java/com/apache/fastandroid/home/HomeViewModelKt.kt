package com.apache.fastandroid.home

import androidx.lifecycle.*
import com.apache.fastandroid.bean.PageInfo
import com.apache.fastandroid.jetpack.BaseViewModel
import com.apache.fastandroid.network.model.Article
import com.apache.fastandroid.network.model.HomeArticleResponse
import com.example.android.architecture.blueprints.todoapp.data.source.TasksDataSource
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * Created by Jerry on 2022/2/23.
 */
class HomeViewModelKt(val reporsitoryKt: HomeReporsitoryKt):BaseViewModel() {


    private val _articleList = MutableLiveData<List<Article>>()
    val articleList: LiveData<List<Article>> = _articleList




    private val _refreshing = MutableLiveData<Boolean>()
    val refreshing: LiveData<Boolean> = _refreshing




    val pageInfo = PageInfo()

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text



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

            val articles:MutableList<Article> = arrayListOf()

            topData.await()?.let {
                articles.addAll(it)
            }
            hotData.await().datas?.let {
                articles.addAll(it)
            }
            _articleList.postValue(articles)


            if (_refreshing.value == true){
                _refreshing.value = false
            }
        }
    }
    fun loadMore(){

        launch({
            loadHotData().datas?.let {
                _articleList.postValue(it)
            }

        },{

        })
    }


}
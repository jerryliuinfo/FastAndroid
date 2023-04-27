package com.apache.fastandroid.demo.app

import androidx.lifecycle.MutableLiveData
import com.apache.fastandroid.demo.app.base.BaseMailRepository
import com.apache.fastandroid.demo.app.base.dataConvert
import com.apache.fastandroid.network.model.ArticleApi
import com.tesla.framework.component.viewstate.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Jerry on 2022/4/18.
 */
class MailRepository(private val loadState:MutableLiveData<State>):BaseMailRepository() {

    suspend fun loadTopArticleCo():List<ArticleApi> {
        var artices = apiService.loadTopArticleCo().dataConvert(loadState)
        return artices
    }
    suspend fun loadTopArticleCo2():List<ArticleApi> = withContext(Dispatchers.IO) {
        var artices = apiService.loadTopArticleCo().dataConvert(loadState)
        return@withContext artices
    }
}
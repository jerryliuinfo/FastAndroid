package com.apache.fastandroid.demo.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apache.fastandroid.demo.app.base.BaseMailViewModel
import com.apache.fastandroid.demo.app.base.initiateRequest
import com.apache.fastandroid.network.model.ArticleApi

/**
 * Created by Jerry on 2022/4/18.
 */
class MailViewModel() : BaseMailViewModel<MailRepository>() {


    private val _topArticleLiveData = MutableLiveData<List<ArticleApi>>()
    val topArticleLiveData:LiveData<List<ArticleApi>> = _topArticleLiveData

    fun loadData(){
        initiateRequest({
            _topArticleLiveData.value = mRepository.loadTopArticleCo()
        },loadState)
    }


}
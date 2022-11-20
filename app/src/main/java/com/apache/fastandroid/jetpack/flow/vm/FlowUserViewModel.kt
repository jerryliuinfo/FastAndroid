package com.apache.fastandroid.jetpack.flow.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apache.fastandroid.home.HomeReporsitoryKt
import com.apache.fastandroid.home.db.HomeDatabase
import com.apache.fastandroid.home.network.HomeNetwork
import com.apache.fastandroid.jetpack.flow.data.bean.User
import com.apache.fastandroid.jetpack.flow.local.DatabaseBuilder
import com.apache.fastandroid.network.model.ArticleApi
import com.blankj.utilcode.util.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Created by Jerry on 2022/6/19.
 */

class FlowUserViewModel:ViewModel() {

    private val mUserDao = DatabaseBuilder.getInstance(Utils.getApp()).userDao()
    private val homeReporsitoryKt = HomeReporsitoryKt.getInstance(HomeDatabase.getHomeDao(), HomeNetwork.getInstance())

    val mArticleState = MutableLiveData<List<ArticleApi>>()


    fun insert(id:Int, name:String){

        viewModelScope.launch {
            mUserDao.insertAll(listOf(User(id,name,"","")))
        }

    }

    fun getAll():Flow<List<User>>{
        return mUserDao.getAllByFlow().catch {  e ->
            e.printStackTrace()
        }.flowOn(Dispatchers.IO)
    }

    fun loadTopArticle(){
        viewModelScope.launch {
           flow {
               val datas = homeReporsitoryKt.loadTopArticleCo()
               emit(datas)
           }.catch { e ->
               e.printStackTrace()
           }.collect{
               mArticleState.value = it
           }
        }
    }
}
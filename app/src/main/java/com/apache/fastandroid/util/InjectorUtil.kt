package com.apache.fastandroid.util

import com.apache.fastandroid.article.ArticleModelFactory
import com.apache.fastandroid.article.ArticleNetwork
import com.apache.fastandroid.article.ArticleReporsitoryKt
import com.apache.fastandroid.demo.mvi.MviViewModel
import com.apache.fastandroid.demo.skydoves.SandWitchRepository
import com.apache.fastandroid.home.HomeModelFactory
import com.apache.fastandroid.home.HomeReporsitoryKt
import com.apache.fastandroid.home.db.HomeDatabase
import com.apache.fastandroid.home.network.HomeNetwork
import com.apache.fastandroid.jetpack.reporsity.UserDao
import com.apache.fastandroid.jetpack.reporsity.UserNetwork
import com.apache.fastandroid.jetpack.reporsity.UserReporsity
import com.apache.fastandroid.jetpack.viewmodel.UserModelFactory


object InjectorUtil {

    @JvmStatic
    private fun getHomeRepository() = HomeReporsitoryKt.getInstance(HomeDatabase.getHomeDao(), HomeNetwork.getInstance())
    @JvmStatic
    private fun getArticleRepository() = ArticleReporsitoryKt.getInstance(ArticleNetwork.getInstance())



    @JvmStatic
    fun getMviModelFactory() = MviViewModel.MviModelFactory(getHomeRepository())


    @JvmStatic
    fun getUserInfoReporsity() = UserReporsity.getInstance(
        UserDao.getInstance(),
        UserNetwork().getInstance()
    )

    @JvmStatic
    fun getUserModelFactory() = UserModelFactory(getUserInfoReporsity())
}
package com.apache.fastandroid.util

import com.apache.fastandroid.article.ArticleModelFactory
import com.apache.fastandroid.article.ArticleNetwork
import com.apache.fastandroid.article.ArticleReporsitoryKt
import com.apache.fastandroid.home.HomeModelFactory
import com.apache.fastandroid.home.HomeReporsitoryKt
import com.apache.fastandroid.home.network.HomeNetwork


object InjectorUtil {

    @JvmStatic
    private fun getHomeRepository() = HomeReporsitoryKt.getInstance(HomeNetwork.getInstance())
    @JvmStatic
    private fun getArticleRepository() = ArticleReporsitoryKt.getInstance(ArticleNetwork.getInstance())

    @JvmStatic
    fun getHomeModelFactory() = HomeModelFactory(getHomeRepository())

    @JvmStatic
    fun getArticeModelFactory() = ArticleModelFactory(getArticleRepository())
}
package com.apache.fastandroid.home.db

import com.apache.fastandroid.network.model.Article
import com.apache.fastandroid.network.model.HomeArticleResponse
import com.blankj.utilcode.util.CacheDiskUtils
import com.blankj.utilcode.util.CacheDoubleUtils
import com.blankj.utilcode.util.CacheMemoryUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Jerry on 2022/2/24.
 */

class HomeDao {


    suspend fun cacheHomeData(pageNum: Int, articleList: HomeArticleResponse) {
        withContext(Dispatchers.IO) {
            CacheDoubleUtils.getInstance(CacheMemoryUtils.getInstance(), CacheDiskUtils.getInstance(getCacheKey(pageNum))).put(getCacheKey(pageNum), articleList)
        }
    }

    suspend fun getCacheHomeData(pageNum: Int): HomeArticleResponse = withContext(Dispatchers.IO) {
        var serializable = CacheDoubleUtils.getInstance(CacheMemoryUtils.getInstance(), CacheDiskUtils.getInstance(getCacheKey(pageNum))).getSerializable(getCacheKey(pageNum))
        return@withContext serializable as HomeArticleResponse
    }





    private fun getCacheKey(pageNum: Int) = "home_${pageNum}"
}
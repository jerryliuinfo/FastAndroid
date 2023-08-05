package com.apache.fastandroid.demo.paging.poster.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apache.fastandroid.network.api.ApiService
import com.apache.fastandroid.network.model.ArticleApi
import com.apache.fastandroid.network.model.HomeArticleResponse
import com.apache.fastandroid.network.model.result.BaseResponse
import com.tesla.framework.component.logger.Logger
import kotlin.math.max
private const val STARTING_KEY = 0
private const val LOAD_DELAY_MILLIS = 3_000L
class PostDataSource(private val apiService: ApiService) : PagingSource<Int, ArticleApi>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleApi> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response:BaseResponse<HomeArticleResponse> = apiService.loadHomeArticleCo(currentLoadingPageKey)


            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1
            Logger.d("PostDataSource load prevKey:$prevKey, nextKey:${currentLoadingPageKey.plus(1)}")

            return LoadResult.Page(
                data = response.data.datas,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }


    /**
     * Makes sure the paging key is never less than [STARTING_KEY]
     */
    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)
    override fun getRefreshKey(state: PagingState<Int, ArticleApi>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val article = state.closestItemToPosition(anchorPosition) ?: return null
        return ensureValidKey(key = article.id - (state.config.pageSize / 2))
    }

}
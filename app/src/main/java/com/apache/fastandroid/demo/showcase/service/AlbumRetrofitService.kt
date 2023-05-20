package com.apache.fastandroid.demo.showcase.service

import com.apache.fastandroid.demo.showcase.bean.SearchAlbumResponse
import com.apache.fastandroid.network.model.ArticleApi
import com.apache.fastandroid.network.model.result.ApiResult
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

internal interface AlbumRetrofitService {

    @POST("./?method=album.search")
    suspend fun searchAlbumAsync(
        @Query("album") phrase: String? = "Jackson",
        @Query("limit") limit: Int = 60,
    ): ApiResult<SearchAlbumResponse>

}

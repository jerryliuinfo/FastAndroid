package com.apache.fastandroid.demo.showcase.data.datasource.api.service

import com.apache.fastandroid.demo.showcase.albumlist.AlbumListViewModel.Companion.DEFAULT_QUERY_NAME
import com.apache.fastandroid.demo.showcase.data.datasource.api.response.GetAlbumInfoResponse
import com.apache.fastandroid.demo.showcase.data.datasource.api.response.SearchAlbumResponse
import com.apache.fastandroid.network.calladapter.apiresult.ApiResult
import retrofit2.http.POST
import retrofit2.http.Query

internal interface AlbumRetrofitService {

    @POST("./?method=album.search")
    suspend fun searchAlbumAsync(
        @Query("album") phrase: String? = DEFAULT_QUERY_NAME,
        @Query("limit") limit: Int = 60,
    ): ApiResult<SearchAlbumResponse>

    @POST("./?method=album.getInfo")
    suspend fun getAlbumInfoAsync(
        @Query("artist") artistName: String,
        @Query("album") albumName: String,
        @Query("mbid") mbId: String?,
    ): ApiResult<GetAlbumInfoResponse>
}

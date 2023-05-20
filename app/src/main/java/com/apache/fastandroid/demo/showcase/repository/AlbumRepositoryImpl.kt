package com.apache.fastandroid.demo.showcase.repository

import com.apache.fastandroid.demo.showcase.bean.Album
import com.apache.fastandroid.demo.showcase.bean.toDomainModel
import com.apache.fastandroid.demo.showcase.service.AlbumRetrofitService
import com.apache.fastandroid.network.model.ArticleApi
import com.apache.fastandroid.network.model.result.ApiResult
import com.apache.fastandroid.network.model.result.Result
import timber.log.Timber

internal class AlbumRepositoryImpl(
    private val albumRetrofitService: AlbumRetrofitService,
//    private val albumDao: AlbumDao,
) : AlbumRepository {

    override suspend fun searchAlbum(phrase: String?): Result<List<Album>> =
        when (val apiResult = albumRetrofitService.searchAlbumAsync(phrase)) {
            is ApiResult.Success -> {
                val albums = apiResult
                    .data
                    .results
                    .albumMatches
                    .album
                    .also { albumsApiModels ->
//                        val albumsEntityModels = albumsApiModels.map { it.toEntityModel() }
//                        albumDao.insertAlbums(albumsEntityModels)
                    }
                    .map { it.toDomainModel() }

                Result.Success(albums)
            }
            is ApiResult.Error -> {
                Result.Failure()
            }
            is ApiResult.Exception -> {
                Timber.e(apiResult.throwable)

//                val albums = albumDao
//                    .getAll()
//                    .map { it.toDomainModel() }

                Result.Success(emptyList())
            }
        }


}

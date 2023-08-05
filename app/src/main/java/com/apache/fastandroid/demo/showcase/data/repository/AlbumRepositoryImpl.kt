package com.apache.fastandroid.demo.showcase.data.repository

import com.apache.fastandroid.demo.showcase.domain.model.Album
import com.apache.fastandroid.demo.showcase.domain.repository.AlbumRepository
import com.apache.fastandroid.network.calladapter.apiresult.ApiResult
import com.apache.fastandroid.demo.showcase.data.datasource.api.model.toDomainModel
import com.apache.fastandroid.demo.showcase.data.datasource.api.model.toEntityModel
import com.apache.fastandroid.demo.showcase.data.datasource.api.service.AlbumRetrofitService
import com.apache.fastandroid.demo.showcase.data.datasource.database.AlbumDao
import com.apache.fastandroid.demo.showcase.data.datasource.database.model.toDomainModel
import timber.log.Timber
import com.apache.fastandroid.network.model.result.Result


internal class AlbumRepositoryImpl(
    private val albumRetrofitService: AlbumRetrofitService,
    private val albumDao: AlbumDao,
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
                        val albumsEntityModels = albumsApiModels.map { it.toEntityModel() }
                        albumDao.insertAlbums(albumsEntityModels)
                    }
                    .map { it.toDomainModel() }

                Result.Success(albums)
            }
            is ApiResult.Error -> {
                Result.Failure()
            }
            is ApiResult.Exception -> {
                Timber.e(apiResult.throwable)

                val albums = albumDao
                    .getAll()
                    .map { it.toDomainModel() }

                Result.Success(albums)
            }
        }

    override suspend fun getAlbumInfo(artistName: String, albumName: String, mbId: String?): Result<Album> =
        when (val apiResult = albumRetrofitService.getAlbumInfoAsync(artistName, albumName, mbId)) {
            is ApiResult.Success -> {
                val album = apiResult
                    .data
                    .album
                    .toDomainModel()

                Result.Success(album)
            }
            is ApiResult.Error -> {
                Result.Failure()
            }
            is ApiResult.Exception -> {
                Timber.e(apiResult.throwable)

                val album = albumDao
                    .getAlbum(artistName, albumName, mbId)
                    .toDomainModel()

                Result.Success(album)
            }
        }

   
}
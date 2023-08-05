package com.apache.fastandroid.demo.showcase.domain.repository

import com.apache.fastandroid.demo.showcase.domain.model.Album

interface AlbumRepository {

    suspend fun getAlbumInfo(artistName: String, albumName: String, mbId: String?): com.apache.fastandroid.network.model.result.Result<Album>

    suspend fun searchAlbum(phrase: String?): com.apache.fastandroid.network.model.result.Result<List<Album>>
}

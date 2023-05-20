package com.apache.fastandroid.demo.showcase.repository

import com.apache.fastandroid.demo.showcase.bean.Album


interface AlbumRepository {


    suspend fun searchAlbum(phrase: String?): com.apache.fastandroid.network.model.result.Result<List<Album>>
}

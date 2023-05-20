package com.apache.fastandroid.demo.showcase.usecase

import com.apache.fastandroid.demo.showcase.bean.Album
import com.apache.fastandroid.demo.showcase.repository.AlbumRepository
import com.apache.fastandroid.network.model.ArticleApi
import com.apache.fastandroid.network.model.result.mapSuccess

class GetAlbumListUseCase(
    private val albumRepository: AlbumRepository,
) {

    suspend  fun query(query: String?): com.apache.fastandroid.network.model.result.Result<List<Album>> {
        val result = albumRepository
            .searchAlbum(query)
            .mapSuccess {
                val albumsWithImages = value.filter { it.getDefaultImageUrl() != null }

                copy(value = albumsWithImages)
            }

        return result
    }
}

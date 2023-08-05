package com.apache.fastandroid.demo.showcase.domain.usecase

import com.apache.fastandroid.demo.showcase.domain.model.Album
import com.apache.fastandroid.demo.showcase.domain.repository.AlbumRepository
import com.apache.fastandroid.network.model.result.mapSuccess
import com.apache.fastandroid.network.model.result.Result

class GetAlbumListUseCase(
    private val albumRepository: AlbumRepository,
) {

    suspend operator fun invoke(query: String?): Result<List<Album>> {
        val result = albumRepository
            .searchAlbum(query)
            .mapSuccess {
                val albumsWithImages = value.filter { it.getDefaultImageUrl() != null }

                copy(value = albumsWithImages)
            }

        return result
    }
}

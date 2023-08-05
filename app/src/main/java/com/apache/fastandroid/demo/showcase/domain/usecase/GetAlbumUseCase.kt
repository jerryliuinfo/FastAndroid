package com.apache.fastandroid.demo.showcase.domain.usecase

import com.apache.fastandroid.demo.showcase.domain.model.Album
import com.apache.fastandroid.demo.showcase.domain.repository.AlbumRepository
import com.apache.fastandroid.network.model.result.Result

internal class GetAlbumUseCase(
    private val albumRepository: AlbumRepository,
) {

    suspend operator fun invoke(
        artistName: String,
        albumName: String,
        mbId: String?,
    ): Result<Album> = albumRepository.getAlbumInfo(artistName, albumName, mbId)
}

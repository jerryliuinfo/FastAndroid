package com.apache.fastandroid.demo.showcase.data.datasource.api.response

import com.apache.fastandroid.demo.showcase.data.datasource.api.model.AlbumApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GetAlbumInfoResponse(
    @SerialName("album") val album: AlbumApiModel,
)

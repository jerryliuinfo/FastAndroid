package com.apache.fastandroid.demo.showcase.data.datasource.api.model

import com.apache.fastandroid.demo.showcase.data.datasource.api.model.AlbumListApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SearchAlbumResultsApiModel(
    @SerialName("albummatches") val albumMatches: AlbumListApiModel,
)

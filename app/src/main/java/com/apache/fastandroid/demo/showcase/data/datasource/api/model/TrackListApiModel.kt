package com.apache.fastandroid.demo.showcase.data.datasource.api.model

import com.apache.fastandroid.demo.showcase.data.datasource.api.model.TrackApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TrackListApiModel(
    @SerialName("track") val track: List<TrackApiModel>,
)

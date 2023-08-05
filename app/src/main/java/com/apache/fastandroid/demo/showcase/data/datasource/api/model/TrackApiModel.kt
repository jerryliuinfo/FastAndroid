package com.apache.fastandroid.demo.showcase.data.datasource.api.model

import com.apache.fastandroid.demo.showcase.data.datasource.database.model.TrackEntityModel
import com.apache.fastandroid.demo.showcase.domain.model.Track
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TrackApiModel(
    @SerialName("name") val name: String,
    @SerialName("duration") val duration: Int? = null,
)

internal fun TrackApiModel.toDomainModel() = Track(
    name = this.name,
    duration = this.duration,
)

internal fun TrackApiModel.toEntityModel() = TrackEntityModel(
    name = this.name,
    duration = this.duration,
)

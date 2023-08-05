package com.apache.fastandroid.demo.showcase.data.datasource.database.model

import com.apache.fastandroid.demo.showcase.domain.model.Track
import kotlinx.serialization.Serializable

@Serializable
internal data class TrackEntityModel(
    val name: String,
    val duration: Int? = null,
)

internal fun TrackEntityModel.toDomainModel() = Track(
    name = this.name,
    duration = this.duration,
)

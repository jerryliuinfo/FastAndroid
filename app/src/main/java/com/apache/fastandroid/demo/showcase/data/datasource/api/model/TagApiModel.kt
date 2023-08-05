package com.apache.fastandroid.demo.showcase.data.datasource.api.model

import com.apache.fastandroid.demo.showcase.data.datasource.database.model.TagEntityModel
import com.apache.fastandroid.demo.showcase.domain.model.Tag
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TagApiModel(
    @SerialName("name") val name: String,
)

internal fun TagApiModel.toDomainModel() = Tag(
    name = this.name,
)

internal fun TagApiModel.toEntityModel() = TagEntityModel(
    name = this.name,
)

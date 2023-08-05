package com.apache.fastandroid.demo.showcase.data.datasource.database.model

import com.apache.fastandroid.demo.showcase.domain.model.Tag
import kotlinx.serialization.Serializable

@Serializable
internal data class TagEntityModel(
    val name: String,
)

internal fun TagEntityModel.toDomainModel() = Tag(
    name = this.name,
)

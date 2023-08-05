package com.apache.fastandroid.demo.showcase.data.datasource.api.model

import com.apache.fastandroid.demo.showcase.data.datasource.api.model.TagApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TagListApiModel(
    @SerialName("tag") val tag: List<TagApiModel>,
)

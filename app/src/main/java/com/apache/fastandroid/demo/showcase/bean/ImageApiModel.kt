package com.apache.fastandroid.demo.showcase.bean

import com.google.gson.annotations.SerializedName

internal data class ImageApiModel(
    @SerializedName("#text") val url: String,
    @SerializedName("size") val size: ImageSizeApiModel,
)

internal fun ImageApiModel.toDomainModel() = Image(
    url = this.url,
    size = this.size.toDomainModel(),
)



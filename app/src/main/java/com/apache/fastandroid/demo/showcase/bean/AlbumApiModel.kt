package com.apache.fastandroid.demo.showcase.bean

import com.google.gson.annotations.SerializedName


internal data class AlbumApiModel(
    @SerializedName("mbid") val mbId: String? = null,
    @SerializedName("name") val name: String,
    @SerializedName("artist") val artist: String,
    @SerializedName("image") val images: List<ImageApiModel>? = null,
)



internal fun AlbumApiModel.toDomainModel(): Album {
    val images = this.images
        ?.filterNot { it.size == ImageSizeApiModel.UNKNOWN || it.url.isBlank() }
        ?.map { it.toDomainModel() }

    return Album(
        mbId = this.mbId,
        name = this.name,
        artist = this.artist,
        images = images ?: listOf(),
    )
}

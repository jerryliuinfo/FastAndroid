package com.apache.fastandroid.demo.showcase.bean

import com.google.gson.annotations.SerializedName


internal enum class ImageSizeApiModel {

    @SerializedName("medium")
    MEDIUM,

    @SerializedName("small")
    SMALL,

    @SerializedName("large")
    LARGE,

    @SerializedName("extralarge")
    EXTRA_LARGE,

    @SerializedName("mega")
    MEGA,

    @SerializedName("")
    UNKNOWN,
}

internal fun ImageSizeApiModel.toDomainModel() = ImageSize.valueOf(this.name)


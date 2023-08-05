package com.apache.fastandroid.demo.showcase.data.datasource.database.model

import com.apache.fastandroid.demo.showcase.domain.enum.ImageSize


internal enum class ImageSizeEntityModel {
    MEDIUM, SMALL, LARGE, EXTRA_LARGE, MEGA
}

internal fun ImageSizeEntityModel.toDomainModel() =
    ImageSize.values().firstOrNull { it.ordinal == this.ordinal }

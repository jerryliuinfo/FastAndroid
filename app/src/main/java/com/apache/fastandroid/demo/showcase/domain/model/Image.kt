package com.apache.fastandroid.demo.showcase.domain.model

import com.apache.fastandroid.demo.showcase.domain.enum.ImageSize

data class Image(
    val url: String,
    val size: ImageSize,
)

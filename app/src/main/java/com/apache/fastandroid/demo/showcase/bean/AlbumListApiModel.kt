package com.apache.fastandroid.demo.showcase.bean

import com.google.gson.annotations.SerializedName


internal data class AlbumListApiModel(
    @SerializedName("album") val album: List<AlbumApiModel>,
)

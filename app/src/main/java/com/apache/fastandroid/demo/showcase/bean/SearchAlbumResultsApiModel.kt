package com.apache.fastandroid.demo.showcase.bean

import com.google.gson.annotations.SerializedName


internal data class SearchAlbumResultsApiModel(
    @SerializedName("albummatches") val albumMatches: AlbumListApiModel,
)

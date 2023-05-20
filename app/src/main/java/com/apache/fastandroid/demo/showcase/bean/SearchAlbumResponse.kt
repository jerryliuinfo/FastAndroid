package com.apache.fastandroid.demo.showcase.bean

import com.google.gson.annotations.SerializedName


internal data class SearchAlbumResponse(
    @SerializedName("results") val results: SearchAlbumResultsApiModel,
)

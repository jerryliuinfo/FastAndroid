package com.apache.fastandroid.network.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ApiUser(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("avatar")
    val avatar: String = ""
): Serializable
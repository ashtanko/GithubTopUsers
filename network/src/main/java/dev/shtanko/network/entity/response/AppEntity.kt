package dev.shtanko.network.entity.response

import com.google.gson.annotations.SerializedName

data class AppEntity(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
    @SerializedName("client_id") val clientId: String
)
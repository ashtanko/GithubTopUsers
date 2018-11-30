package me.shtanko.network.entity

import com.google.gson.annotations.SerializedName

data class UserRemoteEntity(
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
)
package dev.shtanko.network.entity.response

import com.google.gson.annotations.SerializedName

data class UserRemoteEntity(
    @SerializedName("id") val id: Int,
    @SerializedName("login") val login: String? = null,
    @SerializedName("avatar_url") val avatarUrl: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("company") val company: String? = null,
    @SerializedName("blog") val blog: String? = null,
    @SerializedName("bio") val bio: String? = null,
    @SerializedName("public_repos") val publicRepos: Int? = 0,
    @SerializedName("public_gists") val publicGists: Int? = 0,
    @SerializedName("followers") val followers: Int? = 0,
    @SerializedName("following") val following: Int? = 0
){
    companion object {
    }
}
package me.shtanko.network.entity

import com.google.gson.annotations.SerializedName

data class FullUserRemoteEntity(
    @SerializedName("login") val login: String? = null,
    @SerializedName("id") val id: Int,
    @SerializedName("avatar_url") val avatarUrl: String? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("company") val company: String? = null,
    @SerializedName("blog") val blog: String? = null,
    @SerializedName("location") val location: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("bio") val bio: String? = null,
    @SerializedName("public_repos") val publicRepos: Int? = 0,
    @SerializedName("public_gists") val publicGists: Int? = 0,
    @SerializedName("followers") val followers: Int? = 0,
    @SerializedName("following") val following: Int? = 0,
    @SerializedName("created_at") val createdAt: String? = null,
    @SerializedName("updated_at") val updated_at: String? = null
) {
    companion object {
        fun empty(): FullUserRemoteEntity {
            return FullUserRemoteEntity(id = 0)
        }
    }
}
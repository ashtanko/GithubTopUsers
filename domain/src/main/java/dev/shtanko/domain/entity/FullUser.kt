package dev.shtanko.domain.entity

data class FullUser(
    val id: Int,
    val login: String?,
    val avatarUrl: String?,
    val url: String?,
    val name: String?,
    val bio: String?,
    val location: String?,
    val company: String?,
    val blog: String?,
    val publicRepos: Int?,
    val publicGists: Int?,
    val followers: Int?,
    val following: Int?
)
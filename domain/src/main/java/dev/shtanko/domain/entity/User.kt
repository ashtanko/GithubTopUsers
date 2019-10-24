package dev.shtanko.domain.entity

data class User(
    val id: Int,
    val login: String?,
    val avatarUrl: String?
) {
    override fun toString(): String {
        return "User(id=$id, login='$login', avatarUrl='$avatarUrl')"
    }
}
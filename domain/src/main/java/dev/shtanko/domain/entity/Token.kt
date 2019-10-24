package dev.shtanko.domain.entity

data class Token(
    //region api authorization
    val id: Int,
    val url: String?,
    val app: App?,
    val token: String?,
    val hashedToken: String?,
    val tokenLastEight: String?,
    val note: String?,
    val noteUrl: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val scopes: List<String>?,
    //endregion

    //region oauth
    val accessToken: String?,
    val tokenType: String?,
    val scope: String?
    //endregion
) {
    companion object {
        fun empty(): Token {
            return Token(
                -1, "", null, "", "", "",
                "", "", "", "", emptyList(), "",
                "", ""
            )
        }
    }
}
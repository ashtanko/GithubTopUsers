package dev.shtanko.network.entity.response

import com.google.gson.annotations.SerializedName

data class TokenEntity(

    @SerializedName("id") val id: Int,
    @SerializedName("url") val url: String = "",
    @SerializedName("app") val app: AppEntity? = null,
    @SerializedName("token") val token: String = "",
    @SerializedName("hashed_token") val hashedToken: String = "",
    @SerializedName("token_last_eight") val tokenLastEight: String = "",
    @SerializedName("note") val note: String = "",
    @SerializedName("note_url") val noteUrl: String = "",
    @SerializedName("created_at") val createdAt: String = "",
    @SerializedName("updated_at") val updatedAt: String = "",
    @SerializedName("scopes") val scopes: List<String> = emptyList(),
    @SerializedName("access_token") val accessToken: String = "",
    @SerializedName("token_type") val tokenType: String = "",
    @SerializedName("scope") val scope: String = "",
    @SerializedName("error") val error: String = ""

)
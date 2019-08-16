package me.shtanko.network.entity.request

import android.provider.UserDictionary.Words.APP_ID
import com.google.gson.annotations.SerializedName
import me.shtanko.network.CLIENT_ID
import me.shtanko.network.CLIENT_SECRET
import me.shtanko.network.REDIRECT_URL
import me.shtanko.network.SCOPES

data class AuthEntity(
    @SerializedName("client_id") private val clientId: String = CLIENT_ID,
    @SerializedName("client_secret") private val clientSecret: String = CLIENT_SECRET,
    @SerializedName("redirect_uri") private val redirectUri: String = REDIRECT_URL,
    @SerializedName("scopes") private val scopes: List<String> = SCOPES.toList(),
    @SerializedName("state") private val state: String = "",
    @SerializedName("note") private val note: String = APP_ID,
    @SerializedName("note_url") private val noteUrl: String = REDIRECT_URL,
    @SerializedName("opt_code") private val optCode: String = ""
) {
    companion object {
        fun default() = AuthEntity()
    }
}
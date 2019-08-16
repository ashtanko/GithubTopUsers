package me.shtanko.network.api

import kotlinx.coroutines.Deferred
import me.shtanko.network.entity.request.AuthEntity
import me.shtanko.network.entity.response.TokenEntity
import okhttp3.Credentials
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiAuthService {

    @POST(ApiAuthService.AUTHORIZATIONS)
    fun loginAsync(@Body model: AuthEntity = AuthEntity.default()): Deferred<Response<TokenEntity>>

    companion object {

        fun getCredentials(
            username: String,
            password: String
        ): String {
            return Credentials.basic(username, password)
        }

        private const val AUTHORIZATIONS = "authorizations"
    }
}
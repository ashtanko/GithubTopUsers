package dev.shtanko.network

import android.util.Log
import kotlinx.coroutines.Deferred
import dev.shtanko.common.Either
import dev.shtanko.common.Failure
import dev.shtanko.common.android.NetworkHandler
import dev.shtanko.common.exception.OppCodeRequiredException
import dev.shtanko.common.exception.RequestsOverlimitException
import dev.shtanko.common.exception.UnknownServerException
import dev.shtanko.network.api.ApiAuthService
import dev.shtanko.network.api.ApiService
import dev.shtanko.network.entity.response.FullUserRemoteEntity
import dev.shtanko.network.entity.response.TokenEntity
import dev.shtanko.network.entity.response.UserRemoteEntity
import dev.shtanko.network.interceptor.RequestHeaders
import retrofit2.Response
import javax.inject.Inject

interface UserApi {
    suspend fun getUsers(page: Int, perPage: Int, since: Int): Either<Failure, List<UserRemoteEntity>>
    suspend fun getUser(username: String): Either<Failure, FullUserRemoteEntity>
}

interface AuthApi {
    suspend fun login(username: String, password: String): Either<Failure, TokenEntity>
}

interface NetworkClient : UserApi, AuthApi {

}

class Network @Inject constructor(
    private val apiService: ApiService,
    private val apiAuthService: ApiAuthService,
    private val networkHandler: NetworkHandler
) : NetworkClient {


    override suspend fun login(username: String, password: String): Either<Failure, TokenEntity> {

        val authToken = ApiAuthService.getCredentials(username, password)

        RequestHeaders.accessToken = authToken

        return request(apiAuthService.loginAsync(), { it }, TokenEntity(-1))
    }

    override suspend fun getUsers(page: Int, perPage: Int, since: Int): Either<Failure, List<UserRemoteEntity>> {
        return requestOrError(request(apiService.getUsersAsync(page, perPage, since), { it }, emptyList()))
    }

    override suspend fun getUser(username: String): Either<Failure, FullUserRemoteEntity> {
        return requestOrError(request(apiService.getUserAsync(username), { it }, FullUserRemoteEntity.empty()))
    }

    private fun <R> requestOrError(request: Either<Failure, R>): Either<Failure, R> {
        return if (networkHandler.isConnected == true) {
            request
        } else {
            Either.Left(Failure.NetworkConnection)
        }
    }

    private suspend fun <T, R> request(
        response: Deferred<Response<T>>,
        transform: (T) -> R,
        default: T
    ): Either<Failure, R> {

        return try {
            val r = response.await()

            val headers = r.headers().toMultimap()
            val requestsLimit = headers["x-ratelimit-limit"]
            val requestsRemaining = headers["x-ratelimit-remaining"]
            val isOtpRequired = headers["X-GitHub-OTP"]

            val remaining = if (requestsRemaining != null && requestsRemaining.isNotEmpty()) {
                try {
                    requestsRemaining[0].toInt()
                } catch (e: Exception) {
                    0
                }

            } else {
                0
            }

            Log.d(
                "TEST",
                "${r.isSuccessful} isOtpRequiredException:$isOtpRequired HEADERS: $requestsLimit $requestsRemaining $remaining"
            )

            if (r.isSuccessful) {
                Either.Right(transform((r.body() ?: default)))
            } else if (!r.isSuccessful && isOtpRequired != null) {
                Either.Left(Failure.ServerException(OppCodeRequiredException()))
            } else if (remaining == 0) {
                Either.Left(Failure.ServerException(RequestsOverlimitException("Requests overlimit!")))
            } else {
                Either.Left(Failure.ServerException(UnknownServerException(r.message())))
            }

        } catch (exception: Throwable) {
            Either.Left(Failure.ServerError)
        }
    }

}
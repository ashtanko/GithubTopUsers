package me.shtanko.network

import android.util.Log
import kotlinx.coroutines.Deferred
import me.shtanko.common.Either
import me.shtanko.common.Failure
import me.shtanko.network.api.ApiService
import me.shtanko.network.entity.FullUserRemoteEntity
import me.shtanko.network.entity.UserRemoteEntity
import retrofit2.Response
import javax.inject.Inject

interface NetworkClient {

    suspend fun getUsers(page: Int, perPage: Int, since: Int): Either<Failure, List<UserRemoteEntity>>
    suspend fun getUser(username: String): Either<Failure, FullUserRemoteEntity>

}

class Network @Inject constructor(
    private val apiService: ApiService
) : NetworkClient {

    override suspend fun getUsers(page: Int, perPage: Int, since: Int): Either<Failure, List<UserRemoteEntity>> {
        return request(apiService.getUsersAsync(page, perPage, since), { it }, emptyList())
    }

    override suspend fun getUser(username: String): Either<Failure, FullUserRemoteEntity> {
        return request(apiService.getUserAsync(username), { it }, FullUserRemoteEntity.empty())
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

            Log.d("LOL", "HEADERS: $requestsLimit $requestsRemaining")

            when (r.isSuccessful) {
                true -> Either.Right(transform((r.body() ?: default)))
                false -> Either.Left(Failure.ServerError)
            }

        } catch (exception: Throwable) {
            Either.Left(Failure.ServerError)
        }
    }

}
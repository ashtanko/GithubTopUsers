package dev.shtanko.data.repository

import dev.shtanko.common.Either
import dev.shtanko.common.Failure
import dev.shtanko.network.NetworkClient
import dev.shtanko.network.entity.response.FullUserRemoteEntity
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val networkClient: NetworkClient
) {

    suspend fun getUser(username: String): Either<Failure, FullUserRemoteEntity> {
        return networkClient.getUser(username)
    }
}
package me.shtanko.data.repository

import me.shtanko.common.Either
import me.shtanko.common.Failure
import me.shtanko.network.NetworkClient
import me.shtanko.network.entity.FullUserRemoteEntity
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val networkClient: NetworkClient
) {

    suspend fun getUser(username: String): Either<Failure, FullUserRemoteEntity> {
        return networkClient.getUser(username)
    }
}
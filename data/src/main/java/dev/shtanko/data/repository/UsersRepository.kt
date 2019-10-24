package dev.shtanko.data.repository

import dev.shtanko.common.Either
import dev.shtanko.common.Failure
import dev.shtanko.common.map
import dev.shtanko.data.local.model.UserLocalModel
import dev.shtanko.network.NetworkClient
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val networkClient: NetworkClient
) {

    suspend fun getUsers(page: Int, perPage: Int, since: Int): Either<Failure, List<UserLocalModel>> {
        return networkClient.getUsers(page, perPage, since).map { users ->
            users.map { user ->
                UserLocalModel(user.id, user.login, user.avatarUrl)
            }
        }
    }
}
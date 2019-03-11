package me.shtanko.data.repository

import me.shtanko.common.Either
import me.shtanko.common.Failure
import me.shtanko.common.map
import me.shtanko.data.local.model.UserLocalModel
import me.shtanko.domain.entity.FullUser
import me.shtanko.network.NetworkClient
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
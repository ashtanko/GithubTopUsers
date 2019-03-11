package me.shtanko.domain.gateway

import me.shtanko.common.Either
import me.shtanko.common.Failure
import me.shtanko.domain.entity.FullUser
import me.shtanko.domain.entity.User

interface UsersGateway {

    suspend fun getUsers(
        page: Int,
        perPage: Int,
        since: Int
    ): Either<Failure, List<User>>

    suspend fun getUser(username: String): Either<Failure, FullUser>
}
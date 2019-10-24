package dev.shtanko.domain.gateway

import dev.shtanko.common.Either
import dev.shtanko.common.Failure
import dev.shtanko.domain.entity.FullUser
import dev.shtanko.domain.entity.User

interface UsersGateway {

    suspend fun getUsers(
        page: Int,
        perPage: Int,
        since: Int
    ): Either<Failure, List<User>>

    suspend fun getUser(username: String): Either<Failure, FullUser>
}
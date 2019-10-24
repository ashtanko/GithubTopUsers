package dev.shtanko.domain.interactor

import dev.shtanko.common.Either
import dev.shtanko.common.Failure
import dev.shtanko.domain.UseCase
import dev.shtanko.domain.entity.FullUser
import dev.shtanko.domain.gateway.UsersGateway
import javax.inject.Inject

class GetUser @Inject constructor(
    private val usersGateway: UsersGateway
) : UseCase<FullUser, GetUser.Params>() {
    override suspend fun run(params: Params): Either<Failure, FullUser> {
        val (username) = params
        return usersGateway.getUser(username)
    }

    data class Params(val username: String)
}
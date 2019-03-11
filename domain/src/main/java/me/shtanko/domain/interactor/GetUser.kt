package me.shtanko.domain.interactor

import me.shtanko.common.Either
import me.shtanko.common.Failure
import me.shtanko.domain.UseCase
import me.shtanko.domain.entity.FullUser
import me.shtanko.domain.gateway.UsersGateway
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
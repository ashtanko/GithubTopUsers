package me.shtanko.domain.interactor

import me.shtanko.common.Either
import me.shtanko.common.Failure
import me.shtanko.domain.UseCase
import me.shtanko.domain.entity.User
import me.shtanko.domain.gateway.UsersGateway
import javax.inject.Inject

class GetUsers @Inject constructor(
    private val usersGateway: UsersGateway
) : UseCase<List<User>, GetUsers.Params>() {

    override suspend fun run(params: Params): Either<Failure, List<User>> {
        val (page, perPage, since) = params
        return usersGateway.getUsers(page, perPage, since)
    }

    data class Params(
        val page: Int,
        val perPage: Int,
        val since: Int
    ) {
        companion object {
            fun build(page: Int, perPage: Int, since: Int): Params =
                Params(page = page, perPage = perPage, since = since)
        }
    }

}
package dev.shtanko.domain.interactor

import dev.shtanko.common.Either
import dev.shtanko.common.Failure
import dev.shtanko.domain.UseCase
import dev.shtanko.domain.entity.User
import dev.shtanko.domain.gateway.UsersGateway
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
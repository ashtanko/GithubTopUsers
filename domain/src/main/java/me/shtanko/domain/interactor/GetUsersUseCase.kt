package me.shtanko.domain.interactor

import io.reactivex.Observable
import me.shtanko.domain.Schedulers
import me.shtanko.domain.UseCase
import me.shtanko.domain.entity.User
import me.shtanko.domain.gateway.SystemGateway

class GetUsersUseCase constructor(
    schedulers: Schedulers,
    private val systemGateway: SystemGateway
) : UseCase<GetUsersUseCase.Params, List<User>>(schedulers) {


    override fun buildObservable(params: Params?): Observable<List<User>> {
        val page = params?.page ?: 0
        val perPage = params?.perPage ?: 0
        val since = params?.since ?: 0
        return systemGateway.getUsers(page = page, perPage = perPage, since = since)
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
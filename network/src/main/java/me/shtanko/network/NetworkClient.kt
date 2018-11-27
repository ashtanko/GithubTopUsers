package me.shtanko.network

import io.reactivex.Single
import me.shtanko.common.Either
import me.shtanko.common.Failure
import me.shtanko.network.api.GithubApiService
import me.shtanko.data.entity.User
import me.shtanko.network.extension.request
import javax.inject.Inject

interface NetworkClient {
    fun getUsers(
        page: Int = GithubApiService.DEFAULT_PAGE,
        perPage: Int = GithubApiService.DEFAULT_PER_PAGE,
        since: Int = 0
    ): Single<List<User>>
}

class Network @Inject constructor(
    private val service: GithubApiService
) : NetworkClient {

    override fun getUsers(
        page: Int,
        perPage: Int,
        since: Int
    ): Single<List<User>> {
        return service.getUsers(page, perPage, since)
    }

}
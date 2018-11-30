package me.shtanko.network

import io.reactivex.Single
import me.shtanko.network.api.GithubApiService
import me.shtanko.domain.entity.User
import me.shtanko.network.entity.UserRemoteEntity
import javax.inject.Inject

interface NetworkClient {
    fun getUsers(
        page: Int = GithubApiService.DEFAULT_PAGE,
        perPage: Int = GithubApiService.DEFAULT_PER_PAGE,
        since: Int = 0
    ): Single<List<UserRemoteEntity>>
}

class Network @Inject constructor(
    private val service: GithubApiService
) : NetworkClient {

    override fun getUsers(
        page: Int,
        perPage: Int,
        since: Int
    ): Single<List<UserRemoteEntity>> {
        return service.getUsers(page, perPage, since)
    }

}
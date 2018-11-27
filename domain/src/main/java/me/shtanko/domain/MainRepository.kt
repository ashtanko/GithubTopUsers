package me.shtanko.domain

import io.reactivex.Single
import me.shtanko.data.entity.User
import me.shtanko.common.Either
import me.shtanko.common.Failure
import me.shtanko.network.NetworkClient
import me.shtanko.network.api.GithubApiService
import javax.inject.Inject

interface MainRepository {
    fun getUsers(
        page: Int = GithubApiService.DEFAULT_PAGE,
        perPage: Int = GithubApiService.DEFAULT_PER_PAGE,
        since: Int = 0
    ): Single<List<User>>
}

class MainNetwork @Inject constructor(
    private val client: NetworkClient
) : MainRepository {


    override fun getUsers(page: Int, perPage: Int, since: Int): Single<List<User>> {
        return client.getUsers(page, perPage, since)
    }


}
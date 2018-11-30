package me.shtanko.network.api

import io.reactivex.Single
import me.shtanko.domain.entity.User
import me.shtanko.network.entity.UserRemoteEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiService {

    companion object {
        const val DEFAULT_PAGE = 1
        const val DEFAULT_PER_PAGE = 25
    }

    @GET("/users")
    fun getUsers(
        @Query("page")
        page: Int = DEFAULT_PAGE,
        @Query("per_page")
        perPage: Int = DEFAULT_PER_PAGE,
        @Query("since")
        since: Int = 0
    ): Single<List<UserRemoteEntity>>

}
package me.shtanko.network.api

import kotlinx.coroutines.Deferred
import me.shtanko.network.entity.response.FullUserRemoteEntity
import me.shtanko.network.entity.response.UserRemoteEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val DEFAULT_PAGE = 1
        const val DEFAULT_PER_PAGE = 25
        const val DEFAULT_USERNAME = "JakeWharton" //;)
    }

    @GET("/users")
    fun getUsersAsync(
        @Query("page")
        page: Int = DEFAULT_PAGE,
        @Query("per_page")
        perPage: Int = DEFAULT_PER_PAGE,
        @Query("since")
        since: Int = 0
    ): Deferred<Response<List<UserRemoteEntity>>>

    @GET("/users/{username}")
    fun getUserAsync(
        @Path("username")
        username: String = DEFAULT_USERNAME
    ): Deferred<Response<FullUserRemoteEntity>>
}
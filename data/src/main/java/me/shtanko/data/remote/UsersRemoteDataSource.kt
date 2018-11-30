package me.shtanko.data.remote

import io.reactivex.Observable
import io.reactivex.Single
import me.shtanko.domain.entity.User
import me.shtanko.network.NetworkClient
import me.shtanko.network.entity.UserRemoteEntity
import javax.inject.Inject

class UsersRemoteDataSource @Inject constructor(
    private val networkClient: NetworkClient
) {

    fun getAll(page: Int, perPage: Int, since: Int): Observable<List<UserRemoteEntity>> {
        return networkClient.getUsers(page, perPage, since).toObservable()
    }
}
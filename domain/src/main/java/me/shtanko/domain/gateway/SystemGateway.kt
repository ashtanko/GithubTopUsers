package me.shtanko.domain.gateway

import io.reactivex.Observable
import me.shtanko.domain.entity.User

interface SystemGateway {
    fun getUsers(page: Int,
                 perPage: Int,
                 since: Int): Observable<List<User>>
}
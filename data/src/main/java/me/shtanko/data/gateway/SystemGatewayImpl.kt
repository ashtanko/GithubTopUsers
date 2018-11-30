package me.shtanko.data.gateway

import io.reactivex.Observable
import me.shtanko.data.repository.UsersRepository
import me.shtanko.domain.entity.User
import me.shtanko.domain.gateway.SystemGateway
import javax.inject.Inject

class SystemGatewayImpl @Inject constructor(
    private val usersRepository: UsersRepository
) : SystemGateway {

    override fun getUsers(page: Int, perPage: Int, since: Int): Observable<List<User>> {
        return usersRepository.getAll(page, perPage, since).map { users ->
            users.map { user ->
                User(user.id, user.login, user.avatarUrl)
            }
        }
    }
}
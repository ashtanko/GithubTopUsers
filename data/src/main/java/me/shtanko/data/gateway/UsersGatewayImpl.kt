package me.shtanko.data.gateway

import me.shtanko.common.Either
import me.shtanko.common.Failure
import me.shtanko.common.map
import me.shtanko.data.repository.UserRepository
import me.shtanko.data.repository.UsersRepository
import me.shtanko.domain.entity.FullUser
import me.shtanko.domain.entity.User
import me.shtanko.domain.gateway.UsersGateway
import javax.inject.Inject

class UsersGatewayImpl @Inject constructor(
    private val usersRepository: UsersRepository,
    private val userRepository: UserRepository
) : UsersGateway {

    override suspend fun getUser(username: String): Either<Failure, FullUser> {
        return userRepository.getUser(username).map {
            FullUser(
                it.id,
                it.login,
                it.avatarUrl,
                it.url,
                it.name,
                it.bio,
                it.location,
                it.company,
                it.blog,
                it.publicRepos,
                it.publicGists,
                it.followers,
                it.following
            )
        }
    }

    override suspend fun getUsers(page: Int, perPage: Int, since: Int): Either<Failure, List<User>> {
        return usersRepository.getUsers(page, perPage, since).map { users ->
            users.map { user ->
                User(user.id, user.login, user.avatarUrl)
            }
        }
    }

}
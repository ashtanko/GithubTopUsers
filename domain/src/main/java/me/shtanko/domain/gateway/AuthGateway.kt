package me.shtanko.domain.gateway

import me.shtanko.common.Either
import me.shtanko.common.Failure
import me.shtanko.domain.entity.Token

interface AuthGateway {

    suspend fun login(username: String, password: String): Either<Failure, Token>

}
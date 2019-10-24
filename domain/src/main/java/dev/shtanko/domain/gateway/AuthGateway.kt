package dev.shtanko.domain.gateway

import dev.shtanko.common.Either
import dev.shtanko.common.Failure
import dev.shtanko.domain.entity.Token

interface AuthGateway {

    suspend fun login(username: String, password: String): Either<Failure, Token>

}
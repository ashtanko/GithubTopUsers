package dev.shtanko.domain.interactor

import dev.shtanko.common.Either
import dev.shtanko.common.Failure
import dev.shtanko.domain.UseCase
import dev.shtanko.domain.entity.Token
import dev.shtanko.domain.gateway.AuthGateway
import javax.inject.Inject

class ActionLogin @Inject constructor(
    private val authGateway: AuthGateway
) : UseCase<Token, ActionLogin.Params>() {


    override suspend fun run(params: Params): Either<Failure, Token> {
        val (username, password) = params
        return authGateway.login(username, password)
    }


    data class Params(val username: String, val password: String)

}
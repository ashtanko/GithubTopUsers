package me.shtanko.domain.interactor

import me.shtanko.common.Either
import me.shtanko.common.Failure
import me.shtanko.domain.UseCase
import me.shtanko.domain.entity.Token
import me.shtanko.domain.gateway.AuthGateway
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
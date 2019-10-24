package dev.shtanko.data.gateway

import dev.shtanko.common.Either
import dev.shtanko.common.Failure
import dev.shtanko.common.map
import dev.shtanko.domain.entity.App
import dev.shtanko.domain.entity.Token
import dev.shtanko.domain.gateway.AuthGateway
import dev.shtanko.network.NetworkClient
import javax.inject.Inject

class AuthGatewayImpl @Inject constructor(
    private val networkClient: NetworkClient
) : AuthGateway {

    override suspend fun login(username: String, password: String): Either<Failure, Token> {
        return networkClient.login(username, password).map {
            return@map it.run {
                Token(
                    id,
                    url,
                    App(app?.name, app?.url, app?.clientId), //todo
                    token,
                    hashedToken,
                    tokenLastEight,
                    note,
                    noteUrl,
                    createdAt,
                    updatedAt,
                    scopes,
                    accessToken,
                    tokenType,
                    scope
                )
            }
        }
    }
}
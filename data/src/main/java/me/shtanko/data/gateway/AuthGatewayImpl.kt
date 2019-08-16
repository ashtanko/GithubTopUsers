package me.shtanko.data.gateway

import me.shtanko.common.Either
import me.shtanko.common.Failure
import me.shtanko.common.map
import me.shtanko.domain.entity.App
import me.shtanko.domain.entity.Token
import me.shtanko.domain.gateway.AuthGateway
import me.shtanko.network.NetworkClient
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
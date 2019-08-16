package me.shtanko.domian.interactor

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import me.shtanko.common.Either
import me.shtanko.domain.entity.Token
import me.shtanko.domain.gateway.AuthGateway
import me.shtanko.domain.interactor.ActionLogin
import me.shtanko.domian.UnitTest
import org.junit.Before
import org.junit.Test

class ActionLoginTest : UnitTest() {


    companion object {
        private const val USERNAME = "test"
        private const val PASSWORD = "test"
    }

    private val actionLogin: ActionLogin by lazy {
        ActionLogin(authGateway)
    }

    private val authGateway = mock<AuthGateway> {
        on {
            runBlocking {
                login(USERNAME, PASSWORD)
            }
        } doReturn (Either.Right(Token.empty()))
    }

    @Before
    fun setUp() {

    }

    @Test
    fun `should get data`() {
        runBlocking {
            actionLogin.run(ActionLogin.Params(USERNAME, PASSWORD))
        }

        runBlocking {
            verify(authGateway).login(USERNAME, PASSWORD)
        }
    }
}
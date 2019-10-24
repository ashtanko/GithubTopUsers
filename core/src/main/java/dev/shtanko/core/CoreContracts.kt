package dev.shtanko.core

import dev.shtanko.domain.interactor.ActionLogin
import dev.shtanko.domain.interactor.GetUser
import dev.shtanko.domain.interactor.GetUsers

interface ApplicationProvider : MainToolsProvider, RepositoryProvider

interface MainToolsProvider {
    fun provideContext(): App
}

interface RepositoryProvider {
    fun provideGetUser(): GetUser
    fun provideGetUsers(): GetUsers
    fun provideActionLogin(): ActionLogin
}
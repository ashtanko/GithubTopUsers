package me.shtanko.core

import me.shtanko.domain.interactor.ActionLogin
import me.shtanko.domain.interactor.GetUser
import me.shtanko.domain.interactor.GetUsers

interface ApplicationProvider : MainToolsProvider, RepositoryProvider

interface MainToolsProvider {
    fun provideContext(): App
}

interface RepositoryProvider {
    fun provideGetUser(): GetUser
    fun provideGetUsers(): GetUsers
    fun provideActionLogin(): ActionLogin
}
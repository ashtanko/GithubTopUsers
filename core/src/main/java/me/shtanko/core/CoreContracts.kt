package me.shtanko.core

import me.shtanko.domain.interactor.GetUsersUseCase


interface ApplicationProvider : MainToolsProvider, RepositoryProvider

interface MainToolsProvider {
    fun provideContext(): App
}

interface RepositoryProvider {
    fun provideGetUsersUseCase(): GetUsersUseCase
}
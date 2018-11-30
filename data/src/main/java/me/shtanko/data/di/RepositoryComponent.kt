package me.shtanko.data.di

import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import me.shtanko.core.App
import me.shtanko.core.MainToolsProvider
import me.shtanko.core.RepositoryProvider
import me.shtanko.data.AppSchedulers
import me.shtanko.data.gateway.SystemGatewayImpl
import me.shtanko.data.local.dao.UsersDao
import me.shtanko.data.local.db.SystemDatabase
import me.shtanko.domain.Schedulers
import me.shtanko.domain.gateway.SystemGateway
import me.shtanko.domain.interactor.GetUsersUseCase
import me.shtanko.network.di.NetworkComponent
import me.shtanko.network.di.NetworkProvider
import javax.inject.Singleton

@Module
interface RepositoryModule {
    @Binds
    fun bindsSchedulers(impl: AppSchedulers): Schedulers

    @Binds
    fun bindsSystemGateway(impl: SystemGatewayImpl): SystemGateway
}

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideSystemDatabase(app: App): SystemDatabase {
        return SystemDatabase.newInstance(app.getApplicationContext())
    }

    @Provides
    @Singleton
    fun provideUsersDao(systemDatabase: SystemDatabase): UsersDao {
        return systemDatabase.usersDao()
    }

    @Provides
    @Singleton
    fun provideGetUsersUseCase(
        schedulers: Schedulers,
        systemGateway: SystemGateway
    ): GetUsersUseCase {
        return GetUsersUseCase(schedulers, systemGateway)
    }
}

@Singleton
@Component(
    dependencies = [
        NetworkProvider::class,
        MainToolsProvider::class
    ], modules = [
        RepositoryModule::class,
        DataModule::class
    ]
)
interface RepositoryComponent : RepositoryProvider {
    class Initializer private constructor() {

        companion object {
            fun init(mainToolsProvider: MainToolsProvider): RepositoryProvider {
                val networkComponent = NetworkComponent.Initializer.init()
                return DaggerRepositoryComponent.builder()
                    .networkProvider(networkComponent)
                    .mainToolsProvider(mainToolsProvider)
                    .build()
            }
        }
    }
}
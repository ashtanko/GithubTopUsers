package me.shtanko.data.di

import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import me.shtanko.common.android.di.CommonAndroidComponent
import me.shtanko.core.App
import me.shtanko.core.CommonAndroidProvider
import me.shtanko.core.MainToolsProvider
import me.shtanko.core.RepositoryProvider
import me.shtanko.data.gateway.UsersGatewayImpl
import me.shtanko.data.local.dao.UsersDao
import me.shtanko.data.local.db.SystemDatabase
import me.shtanko.domain.gateway.UsersGateway
import me.shtanko.domain.interactor.GetUser
import me.shtanko.domain.interactor.GetUsers
import me.shtanko.network.di.NetworkComponent
import me.shtanko.network.di.NetworkProvider
import javax.inject.Singleton

@Module
interface RepositoryModule {
    @Binds
    fun bindsUserGateway(impl: UsersGatewayImpl): UsersGateway
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
    fun provideGetUser(
        usersGateway: UsersGateway
    ): GetUser {
        return GetUser(usersGateway)
    }


    @Provides
    @Singleton
    fun provideGetUsers(
        usersGateway: UsersGateway
    ): GetUsers {
        return GetUsers(usersGateway)
    }
}

@Singleton
@Component(
    dependencies = [
        NetworkProvider::class,
        MainToolsProvider::class,
        CommonAndroidProvider::class
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
                val commonAndroidProvider = CommonAndroidComponent.Initializer.init()
                return DaggerRepositoryComponent.builder()
                    .networkProvider(networkComponent)
                    .mainToolsProvider(mainToolsProvider)
                    .commonAndroidProvider(commonAndroidProvider)
                    .dataModule(DataModule())
                    .build()
            }
        }
    }
}
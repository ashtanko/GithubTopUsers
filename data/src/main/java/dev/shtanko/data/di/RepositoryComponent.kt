package dev.shtanko.data.di

import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dev.shtanko.core.App
import dev.shtanko.core.MainToolsProvider
import dev.shtanko.core.RepositoryProvider
import dev.shtanko.data.gateway.AuthGatewayImpl
import dev.shtanko.data.gateway.UsersGatewayImpl
import dev.shtanko.data.local.dao.UsersDao
import dev.shtanko.data.local.db.SystemDatabase
import dev.shtanko.domain.gateway.AuthGateway
import dev.shtanko.domain.gateway.UsersGateway
import dev.shtanko.domain.interactor.ActionLogin
import dev.shtanko.domain.interactor.GetUser
import dev.shtanko.domain.interactor.GetUsers
import dev.shtanko.network.di.NetworkComponent
import dev.shtanko.network.di.NetworkProvider
import javax.inject.Singleton

@Module
interface RepositoryModule {
    @Binds
    fun bindsUserGateway(impl: UsersGatewayImpl): UsersGateway

    @Binds
    fun bindsAuthGateway(impl: AuthGatewayImpl): AuthGateway
}

@Module
class UserDataModule {
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

@Module
class RoomModule {
    @Provides
    @Singleton
    fun provideSystemDatabase(app: App): SystemDatabase {
        return SystemDatabase.newInstance(app.getApplicationContext())
    }
}

@Module
class DaoModule {
    @Provides
    @Singleton
    fun provideUsersDao(systemDatabase: SystemDatabase): UsersDao {
        return systemDatabase.usersDao()
    }
}

@Module(includes = [UserDataModule::class, RoomModule::class, DaoModule::class])
class DataModule {


    //region auth use cases
    @Provides
    @Singleton
    fun provideActionLogin(
        authGateway: AuthGateway
    ): ActionLogin {
        return ActionLogin(authGateway)
    }
    //endregion

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
                val networkComponent = NetworkComponent.Initializer.init(mainToolsProvider)
                return DaggerRepositoryComponent.builder()
                    .networkProvider(networkComponent)
                    .mainToolsProvider(mainToolsProvider)
                    .dataModule(DataModule())
                    .build()
            }
        }
    }
}
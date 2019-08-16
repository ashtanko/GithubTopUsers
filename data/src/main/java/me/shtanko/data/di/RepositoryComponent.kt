package me.shtanko.data.di

import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import me.shtanko.core.App
import me.shtanko.core.MainToolsProvider
import me.shtanko.core.RepositoryProvider
import me.shtanko.data.gateway.AuthGatewayImpl
import me.shtanko.data.gateway.UsersGatewayImpl
import me.shtanko.data.local.dao.UsersDao
import me.shtanko.data.local.db.SystemDatabase
import me.shtanko.domain.gateway.AuthGateway
import me.shtanko.domain.gateway.UsersGateway
import me.shtanko.domain.interactor.ActionLogin
import me.shtanko.domain.interactor.GetUser
import me.shtanko.domain.interactor.GetUsers
import me.shtanko.network.di.NetworkComponent
import me.shtanko.network.di.NetworkProvider
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
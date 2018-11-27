package me.shtanko.domain.di

import dagger.Binds
import dagger.Component
import dagger.Module
import me.shtanko.domain.*
import me.shtanko.network.di.DaggerNetworkComponent
import me.shtanko.network.di.NetworkComponent
import me.shtanko.network.di.NetworkProvider
import javax.inject.Singleton

interface RepositoryProvider {
    fun provideMainRepository(): MainRepository
    fun provideDetailsRepository(): DetailsRepository
}

@Module
interface RepositoryModule {
    @Binds
    fun bindsMainRepository(impl: MainNetwork): MainRepository

    @Binds
    fun bindsDetailsRepository(impl: DetailsNetwork): DetailsRepository
}

@Singleton
@Component(dependencies = [NetworkProvider::class], modules = [RepositoryModule::class])
interface RepositoryComponent : RepositoryProvider {
    class Initializer private constructor() {

        companion object {
            fun init(): RepositoryProvider {
                val networkComponent = DaggerNetworkComponent.builder().build()
                return DaggerRepositoryComponent.builder()
                    .networkProvider(networkComponent)
                    .build()
            }
        }
    }
}
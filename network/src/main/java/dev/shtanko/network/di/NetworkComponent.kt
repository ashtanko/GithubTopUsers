package dev.shtanko.network.di

import dagger.Component
import dev.shtanko.core.MainToolsProvider
import dev.shtanko.network.NetworkClient
import javax.inject.Singleton

interface NetworkProvider {
    fun provideNetworkClient(): NetworkClient
}

@Singleton
@Component(
    modules = [RestModule::class, NetworkModule::class],
    dependencies = [MainToolsProvider::class]
)
interface NetworkComponent : NetworkProvider {

    class Initializer private constructor() {
        companion object {
            fun init(mainToolsProvider: MainToolsProvider): NetworkComponent {
                return DaggerNetworkComponent.builder()
                    .mainToolsProvider(mainToolsProvider)
                    .build()
            }
        }
    }

}
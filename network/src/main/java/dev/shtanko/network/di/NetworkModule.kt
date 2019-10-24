package dev.shtanko.network.di

import dagger.Binds
import dagger.Module
import dev.shtanko.network.Network
import dev.shtanko.network.NetworkClient

@Module
interface NetworkModule {
    @Binds
    fun bindsNetworkClient(impl: Network): NetworkClient
}
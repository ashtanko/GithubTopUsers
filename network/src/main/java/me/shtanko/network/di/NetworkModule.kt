package me.shtanko.network.di

import dagger.Binds
import dagger.Module
import me.shtanko.network.Network
import me.shtanko.network.NetworkClient

@Module
interface NetworkModule {
    @Binds
    fun bindsNetworkClient(impl: Network): NetworkClient
}
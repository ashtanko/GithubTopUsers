package me.shtanko.domain.di

import dagger.Module
import dagger.Provides
import me.shtanko.domain.executor.JobExecutor
import me.shtanko.domain.executor.PostExecutionThread
import me.shtanko.domain.executor.ThreadExecutor
import me.shtanko.domain.executor.UIThread
import javax.inject.Singleton

@Module
object JobModule {
    @JvmStatic
    @Provides
    @Singleton
    fun provideThreadExecutor(): ThreadExecutor {
        return JobExecutor()
    }

    @JvmStatic
    @Provides
    @Singleton
    fun providePostExecutionThread(): PostExecutionThread {
        return UIThread()
    }
}
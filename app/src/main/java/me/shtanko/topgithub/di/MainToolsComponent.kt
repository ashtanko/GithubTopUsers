package me.shtanko.topgithub.di

import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import me.shtanko.core.App
import me.shtanko.core.Logger
import me.shtanko.core.MainToolsProvider
import me.shtanko.topgithub.log.PersistentLogger
import javax.inject.Singleton

@Module
object ToolsModule {
}

@Singleton
@Component
interface MainToolsComponent : MainToolsProvider {
    @Component.Builder
    interface Builder {
        fun build(): MainToolsComponent
        @BindsInstance
        fun app(app: App): Builder
    }

    class Initializer private constructor() {
        companion object {

            fun init(app: App): MainToolsProvider =
                DaggerMainToolsComponent.builder()
                    .app(app)
                    .build()
        }
    }
}
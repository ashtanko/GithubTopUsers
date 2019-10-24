package dev.shtanko.topgithub.di

import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dev.shtanko.core.App
import dev.shtanko.core.MainToolsProvider
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
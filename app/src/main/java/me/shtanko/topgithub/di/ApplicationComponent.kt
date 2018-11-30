package me.shtanko.topgithub.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import me.shtanko.core.App
import me.shtanko.core.ApplicationProvider
import me.shtanko.core.MainToolsProvider
import me.shtanko.core.RepositoryProvider
import me.shtanko.data.di.RepositoryComponent
import me.shtanko.topgithub.AndroidApplication
import me.shtanko.topgithub.navigation.Navigator
import me.shtanko.topgithub.platform.BaseActivity
import me.shtanko.topgithub.platform.BaseFragment
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @Singleton
    fun provideNavigation(): Navigator {
        return Navigator()
    }
}

@Singleton
@Component(
    dependencies = [
        MainToolsProvider::class,
        RepositoryProvider::class
    ],
    modules = [
        ViewModelModule::class,
        ApplicationModule::class
    ]
)
interface ApplicationComponent : ApplicationProvider {

    fun inject(application: AndroidApplication)
    fun inject(fragment: BaseFragment)
    fun inject(activity: BaseActivity)

    class Initializer private constructor() {
        companion object {

            fun init(app: AndroidApplication): ApplicationComponent {
                val mainToolsProvider = MainToolsComponent.Initializer.init(app)
                val repositoryProvider = RepositoryComponent.Initializer.init(mainToolsProvider)

                return DaggerApplicationComponent.builder()
                    .mainToolsProvider(mainToolsProvider)
                    .repositoryProvider(repositoryProvider)
                    .build()
            }
        }
    }
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

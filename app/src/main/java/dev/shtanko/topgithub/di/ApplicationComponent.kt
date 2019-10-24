package dev.shtanko.topgithub.di

import dagger.Component
import dev.shtanko.core.ApplicationProvider
import dev.shtanko.core.MainToolsProvider
import dev.shtanko.core.RepositoryProvider
import dev.shtanko.data.di.RepositoryComponent
import dev.shtanko.topgithub.AndroidApplication
import dev.shtanko.topgithub.platform.BaseActivity
import dev.shtanko.topgithub.platform.BaseFragment
import javax.inject.Singleton

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



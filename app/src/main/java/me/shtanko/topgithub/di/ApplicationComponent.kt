package me.shtanko.topgithub.di

import dagger.Component
import me.shtanko.core.ApplicationProvider
import me.shtanko.domain.di.JobModule
import me.shtanko.domain.di.RepositoryComponent
import me.shtanko.domain.di.RepositoryProvider
import me.shtanko.topgithub.AndroidApplication
import me.shtanko.topgithub.platform.BaseFragment
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [RepositoryProvider::class],
    modules = [ViewModelModule::class, JobModule::class]
)
interface ApplicationComponent : ApplicationProvider {
    fun inject(application: AndroidApplication)
    fun inject(fragment: BaseFragment)

    class Initializer private constructor() {
        companion object {

            fun init(): ApplicationComponent {
                val repositoryProvider = RepositoryComponent.Initializer.init()
                return DaggerApplicationComponent.builder()
                    .repositoryProvider(repositoryProvider)
                    .build()
            }

        }
    }
}
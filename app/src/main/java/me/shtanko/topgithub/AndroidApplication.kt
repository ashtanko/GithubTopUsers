package me.shtanko.topgithub

import android.app.Application
import me.shtanko.core.App
import me.shtanko.core.ApplicationProvider
import me.shtanko.topgithub.di.ApplicationComponent

class AndroidApplication : Application(), App {

    val appComponent: ApplicationComponent by lazy { ApplicationComponent.Initializer.init(this@AndroidApplication) }

    override fun getAppComponent(): ApplicationProvider {
        return appComponent
    }

    override fun onCreate() {
        super.onCreate()
        this.injectMembers()
    }

    private fun injectMembers() = appComponent.inject(this)
}
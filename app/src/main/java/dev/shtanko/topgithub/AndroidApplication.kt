package dev.shtanko.topgithub

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.google.firebase.perf.FirebasePerformance
import com.scottyab.rootbeer.RootBeer
import io.fabric.sdk.android.Fabric
import dev.shtanko.core.App
import dev.shtanko.core.ApplicationProvider
import dev.shtanko.topgithub.di.ApplicationComponent
import dev.shtanko.topgithub.log.AndroidLogger
import dev.shtanko.topgithub.log.PersistentLogger

class AndroidApplication : Application(), App {

    val appComponent: ApplicationComponent by lazy { ApplicationComponent.Initializer.init(this@AndroidApplication) }

    override fun getAppComponent(): ApplicationProvider {
        return appComponent
    }

    override fun onCreate() {
        super.onCreate()
        this.injectMembers()

        val rootBeer = RootBeer(this)
        if (rootBeer.isRooted) {
            throw RuntimeException("Device is rooted")
        }

        Fabric.with(this, Crashlytics())

        val myTrace = FirebasePerformance.getInstance().newTrace("test_trace")
        myTrace.start()

        val persistentLogger = PersistentLogger(this.applicationContext)
        val androidLogger = AndroidLogger()
        dev.shtanko.topgithub.log.Log.initialize(androidLogger, persistentLogger)

        dev.shtanko.topgithub.log.Log.v("TAG","MSG")

        //Log.d("TEST_LOGCAT", LogFile.grabLogcat())


    }

    private fun injectMembers() = appComponent.inject(this)
}
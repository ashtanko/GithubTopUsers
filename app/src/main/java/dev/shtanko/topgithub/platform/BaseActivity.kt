package dev.shtanko.topgithub.platform

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dev.shtanko.topgithub.AndroidApplication
import dev.shtanko.topgithub.di.ApplicationComponent
import dev.shtanko.topgithub.navigation.Navigator
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (application as AndroidApplication).appComponent
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    protected inline fun <reified V : ViewModel> bindViewModel() =
            lazy { ViewModelProviders.of(this, viewModelFactory).get(V::class.java) }

}
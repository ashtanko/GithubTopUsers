package dev.shtanko.topgithub.platform

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dev.shtanko.common.android.processing.image.ImageLoader
import dev.shtanko.topgithub.AndroidApplication
import dev.shtanko.topgithub.di.ApplicationComponent
import dev.shtanko.topgithub.navigation.Navigator
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as AndroidApplication).appComponent
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    protected inline fun <reified V : ViewModel> bindViewModel() =
            lazy { ViewModelProviders.of(this, viewModelFactory).get(V::class.java) }
}
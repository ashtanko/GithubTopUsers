package me.shtanko.topgithub.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import me.shtanko.topgithub.ui.details.DetailsViewModel
import me.shtanko.topgithub.ui.main.MainViewModel
import me.shtanko.topgithub.viewmodel.ViewModelFactory
import me.shtanko.topgithub.viewmodel.ViewModelKey

@Module
interface ViewModelModule {
    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindsMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    abstract fun bindsDetailsViewModel(viewModel: DetailsViewModel): ViewModel

}
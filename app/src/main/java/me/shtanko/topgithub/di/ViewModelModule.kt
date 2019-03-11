package me.shtanko.topgithub.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import me.shtanko.topgithub.ui.details.DetailsViewModel
import me.shtanko.topgithub.ui.user.UserViewModel
import me.shtanko.topgithub.ui.users.UsersViewModel
import me.shtanko.topgithub.viewmodel.ViewModelFactory
import me.shtanko.topgithub.viewmodel.ViewModelKey

@Module
interface ViewModelModule {
    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    abstract fun bindsDetailsViewModel(viewModel: DetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UsersViewModel::class)
    abstract fun bindsUsersViewModel(viewModel: UsersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    abstract fun bindsUserViewModel(viewModel: UserViewModel): ViewModel

}
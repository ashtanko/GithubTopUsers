package me.shtanko.topgithub.di

import dagger.Module
import dagger.Provides
import me.shtanko.common.android.processing.image.ImageLoader
import me.shtanko.topgithub.image.GlideImageLoader
import me.shtanko.topgithub.navigation.Navigator
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @Singleton
    fun provideNavigation(): Navigator {
        return Navigator()
    }

    @Provides
    //@Named(GLIDE_IMAGE_LOADER)
    @Singleton
    fun provideImageLoader(): ImageLoader {
        return GlideImageLoader()
    }

    companion object {
        const val GLIDE_IMAGE_LOADER = "GLIDE_IMAGE_LOADER"
    }
}
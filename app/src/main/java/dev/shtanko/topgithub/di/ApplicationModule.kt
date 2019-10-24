package dev.shtanko.topgithub.di

import dagger.Module
import dagger.Provides
import dev.shtanko.common.android.processing.image.ImageLoader
import dev.shtanko.topgithub.image.GlideImageLoader
import dev.shtanko.topgithub.navigation.Navigator
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
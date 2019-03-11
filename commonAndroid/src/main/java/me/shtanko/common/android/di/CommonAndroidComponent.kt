package me.shtanko.common.android.di

import dagger.Component
import me.shtanko.core.CommonAndroidProvider

@Component
interface CommonAndroidComponent : CommonAndroidProvider {

    class Initializer private constructor() {
        companion object {
            fun init(): CommonAndroidProvider {
                return DaggerCommonAndroidComponent.builder().build()
            }
        }
    }

}
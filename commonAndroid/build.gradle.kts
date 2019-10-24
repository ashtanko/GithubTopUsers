import Versions.Android.Build.COMPILE_SDK_VERSION
import Versions.Android.Build.MIN_SDK_VERSION
import Versions.Android.Build.TARGET_SDK_VERSION

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(COMPILE_SDK_VERSION)

    defaultConfig {
        minSdkVersion(MIN_SDK_VERSION)
        targetSdkVersion(TARGET_SDK_VERSION)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), file("proguard-rules.pro"))
        }
    }

}

dependencies {
    //region kotlin
    implementation(Dependencies.Kotlin.Stdlib.CORE)
    //endregion

    //region dependency injection
    implementation(Dependencies.Injection.DAGGER)
    kapt(Dependencies.Injection.COMPILER)
    //endregion
}

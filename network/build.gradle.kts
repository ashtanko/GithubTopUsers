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
        buildConfigField("String", "GITHUB_API_URL", "\"${properties["endpoint"]}\"")
        buildConfigField("String", "GITHUB_CLIENT_ID", "\"${properties["client_id"]}\"")
        buildConfigField("String", "GITHUB_SECRET", "\"${properties["client_secret"]}\"")
        buildConfigField("String", "GITHUB_REDIRECT_URL", "\"${properties["redirect_url"]}\"")
        buildConfigField("String", "GITHUB_API_STATUS_URL", "\"${properties["status_url"]}\"")
        buildConfigField("String", "GITHUB_OAUTH_URL", "\"${properties["oauth_url"]}\"")
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
    implementation(Dependencies.Kotlin.COROUTINES)
    implementation(Dependencies.Kotlin.COROUTINES_ANDROID)
    //endregion

    //region network/io
    api(Dependencies.Network.RETROFIT)
    implementation(Dependencies.Network.RETROFIT_CONVERTER_GSON)
    implementation(Dependencies.Network.OKHTTP)
    implementation(Dependencies.Network.GSON)
    implementation(Dependencies.Network.LOGGING_INTERCEPTOR)
    implementation(Dependencies.Network.RETROFIT_CONVERTER_GSON)
    implementation(Dependencies.Network.RETROFIT_COROUTINES_ADAPTER)
    //endregion

    //region dependency injection
    implementation(Dependencies.Injection.DAGGER)
    kapt(Dependencies.Injection.COMPILER)
    //endregion
    implementation(project(Modules.DOMAIN))
    implementation(project(Modules.COMMON_KOTLIN))
    implementation(project(Modules.COMMON_ANDROID))
    implementation(project(Modules.CORE))
}
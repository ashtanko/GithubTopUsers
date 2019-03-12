plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(Versions.Android.Build.compileSdkVersion)

    defaultConfig {
        minSdkVersion(Versions.Android.Build.minSdkVersion)
        targetSdkVersion(Versions.Android.Build.targetSdkVersion)
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "GITHUB_API_URL", "\"https://api.github.com/\"")
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
    implementation(Dependencies.Kotlin.Stdlib.core)
    implementation(Dependencies.Kotlin.coroutines)
    //endregion

    //region network/io
    api(Dependencies.retrofit)
    implementation(Dependencies.retrofitConverterGson)
    implementation(Dependencies.okhttp3)
    implementation(Dependencies.gson)
    implementation(Dependencies.loggingInterceptor)
    implementation(Dependencies.retrofitConverterGson)
    implementation(Dependencies.kotlinCoroutinesAdapter)
    //endregion

    //region dependency injection
    implementation(Dependencies.dagger)
    kapt(Dependencies.daggerCompiler)
    //endregion
    implementation(project(":domain"))
    implementation(project(":commonKotlin"))
}
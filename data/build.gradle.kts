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

    implementation(project(":network"))
    implementation(project(":domain"))
    implementation(project(":core"))

    //region kotlin
    implementation(Dependencies.Kotlin.Stdlib.core)
    //endregion

    //region dependency injection
    implementation(Dependencies.dagger)
    kapt(Dependencies.daggerCompiler)
    //endregion

    //
    implementation(Dependencies.roomRuntime)
    kapt(Dependencies.roomCompiler)

    implementation(project(":commonAndroid"))
    implementation(project(":commonKotlin"))
}
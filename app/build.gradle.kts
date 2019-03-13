plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(Versions.Android.Build.compileSdkVersion)

    defaultConfig {
        applicationId = "me.shtanko.topgithub"
        minSdkVersion(Versions.Android.Build.minSdkVersion)
        targetSdkVersion(Versions.Android.Build.targetSdkVersion)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    lintOptions {
        isAbortOnError = false
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
    //endregion

    //region support
    implementation(Dependencies.appcompat)
    implementation(Dependencies.recyclerview)
    implementation(Dependencies.Lifecycle.livedata)
    implementation(Dependencies.Lifecycle.extensions)
    kapt(Dependencies.Lifecycle.compiler)
    implementation(Dependencies.constraintlayout)
    //endregion

    //region dependency injection
    implementation(Dependencies.dagger)
    kapt(Dependencies.daggerCompiler)
    //endregion

    //region image processing
    implementation(Dependencies.glide)
    kapt(Dependencies.glideCompiler)
    //endregion

    implementation(Dependencies.material)

    //region unit testing
    testImplementation(TestDependencies.junit)
    //endregion

    //region ui testing
    testImplementation(TestDependencies.runner)
    testImplementation(TestDependencies.espresso)

    api(Dependencies.retrofit)

    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":core"))
    implementation(project(":commonAndroid"))
    implementation(project(":commonKotlin"))
    //endregion
}

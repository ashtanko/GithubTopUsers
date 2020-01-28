import Versions.Android.Build.COMPILE_SDK_VERSION
import Versions.Android.Build.MIN_SDK_VERSION
import Versions.Android.Build.TARGET_SDK_VERSION

plugins {
    id("com.android.application")
    id("io.fabric")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(COMPILE_SDK_VERSION)

    defaultConfig {
        applicationId = "dev.shtanko.topgithub"
        minSdkVersion(MIN_SDK_VERSION)
        targetSdkVersion(TARGET_SDK_VERSION)
        versionCode = 2
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    lintOptions {
        isAbortOnError = false
    }

    signingConfigs {
        getByName("debug") {

        }

        create("release") {
            try {
                storeFile = file("${properties["SHTANKO_KEY_STORE_FILE"]}")
                storePassword = "${properties["SHTANKO_KEY_STORE_PASSWORD"]}"
                keyAlias = "${properties["SHTANKO_KEY_ALIAS"]}"
                keyPassword = "${properties["SHTANKO_KEY_PASSWORD"]}"
            } catch (e: Error) {
                throw InvalidUserDataException("You should define KEYSTORE_PASSWORD and KEY_PASSWORD in gradle.properties.")
            }
        }
    }

    splits {
        abi {
            isEnable = true
            reset()
            //include("arm64-v8a", "armeabi-v7a", "x86", "x86_64")
            isUniversalApk = true
        }
    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
            resValue("string", "fabric_api_key", "${properties["fabric_api_key"]}")
        }

        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            resValue("string", "fabric_api_key", "${properties["fabric_api_key"]}")
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(getDefaultProguardFile("proguard-android.txt"))
            proguardFiles(file("proguard-rules.pro"))
        }
    }

    dataBinding {
        isEnabled = true
    }
}

dependencies {

    //region kotlin
    implementation(Dependencies.Kotlin.Stdlib.CORE)
    //endregion

    //region support
    implementation(Dependencies.AndroidSdk.APPCOMPAT)
    implementation(Dependencies.AndroidSdk.RECYCLER_VIEW)
    implementation(Dependencies.Lifecycle.LIVEDATA)
    implementation(Dependencies.Lifecycle.EXTENSIONS)
    implementation(Dependencies.Lifecycle.VIEWMODEL)
    implementation(Dependencies.Lifecycle.KTX)
    kapt(Dependencies.Lifecycle.COMPILER)
    implementation(Dependencies.AndroidSdk.CONSTRAINTLAYOUT)
    //endregion

    implementation(Dependencies.Other.ROOTBEER)

    implementation(Dependencies.Firebase.CORE)
    implementation(Dependencies.Firebase.ANALYTICS)
    implementation(Dependencies.Firebase.CRASH)
    implementation(Dependencies.Firebase.CRASHLYTICS)
    implementation(Dependencies.Firebase.PERFORMANCE)

    implementation(Dependencies.Kotlin.COROUTINES)
    implementation(Dependencies.Kotlin.COROUTINES_ANDROID)

    implementation(Dependencies.Other.CRASHLYTICS) {
        isTransitive = true
    }

    implementation(Dependencies.Other.SECURED_PREFERENCE_STORE)

    //region dependency injection
    implementation(Dependencies.Injection.DAGGER)
    kapt(Dependencies.Injection.COMPILER)
    //endregion

    //region image processing
    implementation(Dependencies.ImageProcessor.GLIDE)
    kapt(Dependencies.ImageProcessor.GLIDE_COMPILER)
    //endregion

    implementation(Dependencies.AndroidSdk.MATERIAL)

    //region unit testing
    testImplementation(TestDependencies.JUNIT)
    //endregion

    //region ui testing
    testImplementation(TestDependencies.RUNNER)
    testImplementation(TestDependencies.ESPRESSO)

    //api(Dependencies.retrofit)

    implementation(project(Modules.DATA))
    implementation(project(Modules.DOMAIN))
    implementation(project(Modules.CORE))
    implementation(project(Modules.COMMON_ANDROID))
    implementation(project(Modules.COMMON_KOTLIN))
    //endregion
}

apply {
    plugin("com.google.gms.google-services")
}
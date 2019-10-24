import Versions.APPCOMPAT_VERSION
import Versions.DAGGER_VERSION
import Versions.GLIDE_VERSION
import Versions.GOOGLE_SERVICES_VERSION
import Versions.GRADLE_PLUGIN_VERSION
import Versions.KOTLIN_COROUTINES_VERSION
import Versions.KOTLIN_VERSION
import Versions.LIFECYCLE_VERSION
import Versions.OKHTTP_VERSION
import Versions.RETROFIT_VERSION
import Versions.ROOM_VERSION

object Dependencies {


    object App {
        const val GRADLE_PLUGIN = "com.android.tools.build:gradle:$GRADLE_PLUGIN_VERSION"
        const val GOOGLE_SERVICES = "com.google.gms:google-services:$GOOGLE_SERVICES_VERSION"
        const val FABRIC = "io.fabric.tools:gradle:1.25.4"
    }

    object AndroidSdk {
        const val APPCOMPAT = "androidx.appcompat:appcompat:$APPCOMPAT_VERSION"
        const val RECYCLER_VIEW = "androidx.recyclerview:recyclerview:1.0.0"
        const val CONSTRAINTLAYOUT = "androidx.constraintlayout:constraintlayout:2.0.0-beta3"
        const val MATERIAL = "com.google.android.material:material:1.2.0-alpha01"
    }

    object Other {
        const val ROOTBEER = "com.scottyab:rootbeer-lib:0.0.7"
        const val CRASHLYTICS = "com.crashlytics.sdk.android:crashlytics:2.9.9@aar"
        const val SECURED_PREFERENCE_STORE = "online.devliving:securedpreferencestore:0.7.4"
    }


    object Kotlin {

        object Build {
            const val KOTLIN_GRADLE_PLUGIN =
                "org.jetbrains.kotlin:kotlin-gradle-plugin:$KOTLIN_VERSION"
        }

        object Stdlib {
            const val CORE = "org.jetbrains.kotlin:kotlin-stdlib:$KOTLIN_VERSION"
        }

        const val COROUTINES =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:$KOTLIN_COROUTINES_VERSION"
        const val COROUTINES_ANDROID =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:$KOTLIN_COROUTINES_VERSION"
    }

    object Injection {
        const val DAGGER = "com.google.dagger:dagger:$DAGGER_VERSION"
        const val COMPILER = "com.google.dagger:dagger-compiler:$DAGGER_VERSION"
    }

    object Room {
        const val COMPILER = "android.arch.persistence.room:compiler:$ROOM_VERSION"
        const val RUNTIME = "android.arch.persistence.room:runtime:$ROOM_VERSION"
        const val COROUTINES = "androidx.room:room-coroutines:$ROOM_VERSION"
    }

    object Network {
        const val RETROFIT = "com.squareup.retrofit2:retrofit:$RETROFIT_VERSION"
        const val RETROFIT_CONVERTER_GSON =
            "com.squareup.retrofit2:converter-gson:$RETROFIT_VERSION"
        const val RETROFIT_COROUTINES_ADAPTER =
            "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2"
        const val OKHTTP = "com.squareup.okhttp3:okhttp:$OKHTTP_VERSION"
        const val LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:$OKHTTP_VERSION"
        const val GSON = "com.google.code.gson:gson:2.8.5"
    }

    object ImageProcessor {
        const val GLIDE = "com.github.bumptech.glide:glide:$GLIDE_VERSION"
        const val GLIDE_COMPILER = "com.github.bumptech.glide:compiler:$GLIDE_VERSION"
    }

    object Lifecycle {
        const val LIVEDATA = "androidx.lifecycle:lifecycle-livedata:$LIFECYCLE_VERSION"
        const val EXTENSIONS = "androidx.lifecycle:lifecycle-extensions:$LIFECYCLE_VERSION"
        const val COMPILER = "androidx.lifecycle:lifecycle-compiler:$LIFECYCLE_VERSION"
        const val VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel:$LIFECYCLE_VERSION"
        const val KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:$LIFECYCLE_VERSION"
    }

    object Firebase {
        const val CORE = "com.google.firebase:firebase-core:16.0.8"
        const val ANALYTICS = "com.google.firebase:firebase-analytics:16.4.0"
        const val CRASH = "com.google.firebase:firebase-crash:16.2.1"
        const val CRASHLYTICS = "com.crashlytics.sdk.android:crashlytics:2.9.9"
        const val PERFORMANCE = "com.google.firebase:firebase-perf:16.2.4"
    }
}
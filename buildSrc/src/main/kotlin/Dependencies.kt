object Dependencies {

    const val gradlePlugin = "com.android.tools.build:gradle:${Versions.gradlePlugin}"


    object Kotlin {

        object Build {
            const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        }


        object Stdlib {
            const val core = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
        }

        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    }


    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"

    const val roomCompiler = "android.arch.persistence.room:compiler:${Versions.room}"
    const val roomRuntime = "android.arch.persistence.room:runtime:${Versions.room}"
    const val roomCoroutines = "androidx.room:room-coroutines:${Versions.room}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val kotlinCoroutinesAdapter = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2"

    const val okhttp3 = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    const val gson = "com.google.code.gson:gson:2.8.5"

    const val appcompat = "androidx.appcompat:appcompat:${Versions.androidSupport}"
    const val recyclerview = "androidx.recyclerview:recyclerview:1.0.0"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:1.1.3"

    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"

    const val material = "com.google.android.material:material:1.0.0"

    object Lifecycle {
        const val livedata = "androidx.lifecycle:lifecycle-livedata:${Versions.lifecycle}"
        const val extensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
        const val compiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"
    }

}
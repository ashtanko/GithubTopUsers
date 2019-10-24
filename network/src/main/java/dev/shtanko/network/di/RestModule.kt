package dev.shtanko.network.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dev.shtanko.common.android.NetworkHandler
import dev.shtanko.core.App
import dev.shtanko.network.GITHUB_API_URL
import dev.shtanko.network.GSON_DATE_FORMAT
import dev.shtanko.network.api.ApiAuthService
import dev.shtanko.network.api.ApiService
import dev.shtanko.network.interceptor.AuthInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object RestModule {

    @Provides
    @JvmStatic
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().setPrettyPrinting()
            .setDateFormat(GSON_DATE_FORMAT)
            .create()
    }

    @Provides
    @JvmStatic
    @Singleton
    fun provideConvertersFactory(gson: Gson): Converter.Factory =
        GsonConverterFactory.create(gson)

    @Provides
    @JvmStatic
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @JvmStatic
    @Singleton
    fun provideAuthInterceptor() = AuthInterceptor()

    @Provides
    @JvmStatic
    @Singleton
    fun provideOkHttpCache(app: App): Cache {
        return Cache(app.getApplicationContext().cacheDir, CACHE_SIZE)
    }

    @Provides
    @JvmStatic
    @Singleton
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor,
        cache: Cache
    ): OkHttpClient =
        OkHttpClient().newBuilder()
            .cache(cache)
            .addNetworkInterceptor(interceptor)
            .addNetworkInterceptor(authInterceptor)
            .connectTimeout(CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()

    @Provides
    @JvmStatic
    @Singleton
    fun provideRetrofitBuilder(
        client: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit.Builder = Retrofit.Builder()
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(converterFactory)
        .client(client)

    @Provides
    @JvmStatic
    @Singleton
    fun provideApiService(builder: Retrofit.Builder): ApiService = builder
        .baseUrl(GITHUB_API_URL)
        .build()
        .create(ApiService::class.java)

    @Provides
    @JvmStatic
    @Singleton
    fun provideNetworkHandler(app: App): NetworkHandler {
        return NetworkHandler(app.getApplicationContext())
    }

    @Provides
    @JvmStatic
    @Singleton
    fun provideAuthApiService(builder: Retrofit.Builder): ApiAuthService = builder
        .baseUrl(GITHUB_API_URL)
        .build()
        .create(ApiAuthService::class.java)

    private const val CACHE_SIZE: Long = 10 * 1024 * 1024 // 10MB
    private const val CONNECTION_TIMEOUT_SECONDS = 120L
    private const val READ_TIMEOUT_SECONDS = 120L
    private const val WRITE_TIMEOUT_SECONDS = 120L

}
package me.shtanko.network.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import me.shtanko.network.GITHUB_API_URL
import me.shtanko.network.GSON_DATE_FORMAT
import me.shtanko.network.api.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient().newBuilder()
            .addNetworkInterceptor(interceptor)
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
}
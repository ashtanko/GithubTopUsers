package me.shtanko.network.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import me.shtanko.network.GITHUB_API_URL
import me.shtanko.network.GSON_DATE_FORMAT
import me.shtanko.network.api.GithubApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
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
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(converterFactory)
        .client(client)

    @Provides
    @JvmStatic
    @Singleton
    fun provideGithubApiService(builder: Retrofit.Builder): GithubApiService = builder
        .baseUrl(GITHUB_API_URL)
        .build()
        .create(GithubApiService::class.java)
}
package ru.yofik.athena.common.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.yofik.athena.common.data.api.ApiConstants
import ru.yofik.athena.common.data.api.UserApi
import ru.yofik.athena.common.data.api.interceptors.LoggingInterceptor
import ru.yofik.athena.common.data.api.interceptors.NetworkStatusInterceptor

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideUserApi(builder: Retrofit.Builder): UserApi {
        return builder.build().create(UserApi::class.java)
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit.Builder {

        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_ENDPOINT)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
    }

    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        networkStatusInterceptor: NetworkStatusInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(networkStatusInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    fun provideHttpLoggingInterceptor(
        loggingInterceptor: LoggingInterceptor
    ): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(loggingInterceptor)

        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return interceptor
    }
}

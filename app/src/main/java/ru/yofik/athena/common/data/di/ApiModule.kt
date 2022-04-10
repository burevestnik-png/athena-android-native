package ru.yofik.athena.common.data.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.yofik.athena.BuildConfig
import ru.yofik.athena.common.data.api.ApiConstants
import ru.yofik.athena.common.data.api.UserApi
import ru.yofik.athena.common.data.api.interceptors.AuthenticationInterceptor
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
        networkStatusInterceptor: NetworkStatusInterceptor,
        authenticationInterceptor: AuthenticationInterceptor
    ): OkHttpClient {
        val builder =
            OkHttpClient.Builder()
                .addInterceptor(networkStatusInterceptor)
                .addInterceptor(authenticationInterceptor)

        if (BuildConfig.DEBUG) builder.addNetworkInterceptor(StethoInterceptor())

        return builder.build()
    }
}

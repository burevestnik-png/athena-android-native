package ru.yofik.athena.common.data.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.yofik.athena.common.BuildConfig
import ru.yofik.athena.common.data.api.ApiHttpConstants
import ru.yofik.athena.common.data.api.ApiParameters.AUTH_HEADER
import ru.yofik.athena.common.data.api.ApiParameters.TOKEN_TYPE
import ru.yofik.athena.common.data.api.ApiWsConstants
import ru.yofik.athena.common.data.api.common.interceptors.LoggingInterceptor
import ru.yofik.athena.common.data.api.common.interceptors.NetworkStatusInterceptor
import ru.yofik.athena.common.data.api.http.interceptors.AuthenticationInterceptor
import ru.yofik.athena.common.data.api.http.model.chat.ChatApi
import ru.yofik.athena.common.data.api.http.model.message.MessageApi
import ru.yofik.athena.common.data.api.http.model.user.UserApi
import ru.yofik.athena.common.data.api.http.model.userProfiles.UserProfileApi
import ru.yofik.athena.common.data.api.ws.listeners.NotificationListener
import ru.yofik.athena.common.data.preferences.Preferences
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MessengerRetrofit

@Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {

    @Provides
    @Singleton
    fun provideChatApi(@MessengerRetrofit builder: Builder): ChatApi {
        return builder.build().create(ChatApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserProfileApi(@MessengerRetrofit builder: Builder): UserProfileApi {
        return builder.build().create(UserProfileApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApi(@AuthRetrofit builder: Builder): UserApi {
        return builder.build().create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMessageApi(@MessengerRetrofit builder: Builder): MessageApi {
        return builder.build().create(MessageApi::class.java)
    }

    @Provides
    @MessengerRetrofit
    fun provideMessengerRetrofit(okHttpClient: OkHttpClient): Builder {
        return Builder()
            .baseUrl(ApiHttpConstants.BASE_URL_MESSENGER_SERVICE)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
    }

    @Provides
    @AuthRetrofit
    fun provideAuthRetrofit(okHttpClient: OkHttpClient): Builder {
        return Builder()
            .baseUrl(ApiHttpConstants.BASE_URL_AUTH_SERVICE)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
    }

    @Provides
    fun provideHttpLoggingInterceptor(
        loggingInterceptor: LoggingInterceptor
    ): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(loggingInterceptor)
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    fun provideOkHttpClient(
        networkStatusInterceptor: NetworkStatusInterceptor,
        authenticationInterceptor: AuthenticationInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val builder =
            OkHttpClient.Builder()
                .addInterceptor(networkStatusInterceptor)
                .addInterceptor(authenticationInterceptor)
                .addInterceptor(httpLoggingInterceptor)

        if (BuildConfig.DEBUG) builder.addNetworkInterceptor(StethoInterceptor())

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideNotificationWebsocket(
        networkStatusInterceptor: NetworkStatusInterceptor,
        notificationListener: NotificationListener,
        preferences: Preferences
    ): WebSocket {
        val client =
            OkHttpClient.Builder()
                .addInterceptor(networkStatusInterceptor)
                .pingInterval(60, TimeUnit.SECONDS)
                .build()

        val accessToken = preferences.getTokens().accessToken
        val request =
            Request.Builder()
                .url(ApiWsConstants.WS_NOTIFICATION_ENDPOINT)
                .addHeader(AUTH_HEADER, "$TOKEN_TYPE $accessToken")
                .build()

        Timber.d("Auth header: ${request.header(AUTH_HEADER)}")
        return client.newWebSocket(request, notificationListener)
    }

    // todo need to know a bit more about @Qualifier and DI
    /*@Provides
    fun provideWebsocketClient(): OkHttpClient {
        return OkHttpClient.Builder().pingInterval(60, TimeUnit.SECONDS).build()
    }*/
}

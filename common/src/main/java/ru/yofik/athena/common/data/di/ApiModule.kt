package ru.yofik.athena.common.data.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
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

@Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {

    @Provides
    @Singleton
    fun provideChatApi(builder: Retrofit.Builder): ChatApi {
        return builder.build().create(ChatApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserProfileApi(builder: Retrofit.Builder): UserProfileApi {
        return builder.build().create(UserProfileApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApi(builder: Builder): UserApi {
        return builder.build().create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMessageApi(builder: Builder): MessageApi {
        return builder.build().create(MessageApi::class.java)
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(ApiHttpConstants.BASE_ENDPOINT)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
    }

    @Provides
    fun provideHttpLoggingInterceptor(loggingInterceptor: LoggingInterceptor): HttpLoggingInterceptor {
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

        val accessToken = preferences.getAccessToken()
        val request =
            Request.Builder()
                .url(ApiWsConstants.WS_NOTIFICATION_ENDPOINT)
                .addHeader(AUTH_HEADER, "$TOKEN_TYPE ${BuildConfig.CLIENT_TOKEN} $accessToken")
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

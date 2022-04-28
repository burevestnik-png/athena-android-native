package ru.yofik.athena.common.data.api.interceptors

import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import ru.yofik.athena.common.BuildConfig
import ru.yofik.athena.common.data.api.ApiConstants.CLIENT_TOKEN
import ru.yofik.athena.common.data.api.ApiParameters.AUTH_HEADER
import ru.yofik.athena.common.data.api.ApiParameters.NO_AUTH_HEADER
import ru.yofik.athena.common.data.api.ApiParameters.TOKEN_TYPE
import ru.yofik.athena.common.data.preferences.Preferences
import timber.log.Timber

class AuthenticationInterceptor @Inject constructor(private val preferences: Preferences) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = preferences.getAccessToken()
        Timber.d("token $accessToken")
        val request = chain.request()

        Timber.d("request headers: ${request.headers.joinToString(" ")}")
        if (request.headers[NO_AUTH_HEADER] != null) {
            Timber.d("request has no auth header")
            return chain.proceed(chain.createRequestWithClientToken())
        }

        return chain.proceed(chain.createRequestWithClientAndUserTokens(accessToken))
    }

    private fun Interceptor.Chain.createRequestWithClientToken(): Request {
        return request()
            .newBuilder()
            .addHeader(AUTH_HEADER, "$TOKEN_TYPE ${BuildConfig.CLIENT_TOKEN}")
            .build()
    }

    private fun Interceptor.Chain.createRequestWithClientAndUserTokens(accessToken: String): Request {
        return request()
            .newBuilder()
            .addHeader(AUTH_HEADER, "$TOKEN_TYPE ${BuildConfig.CLIENT_TOKEN} $accessToken")
            .build()
    }
}

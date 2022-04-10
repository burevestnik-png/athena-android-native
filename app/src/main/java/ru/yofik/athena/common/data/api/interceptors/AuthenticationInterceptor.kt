package ru.yofik.athena.common.data.api.interceptors

import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
    }
}

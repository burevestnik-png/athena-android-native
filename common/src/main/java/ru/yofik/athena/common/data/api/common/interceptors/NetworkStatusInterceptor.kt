package ru.yofik.athena.common.data.api.common.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import ru.yofik.athena.common.data.api.ConnectionManager
import ru.yofik.athena.common.domain.model.exceptions.NetworkUnavailableException
import javax.inject.Inject

internal class NetworkStatusInterceptor
@Inject
constructor(private val connectionManager: ConnectionManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (connectionManager.isConnected()) {
            chain.proceed(chain.request())
        } else {
            throw NetworkUnavailableException()
        }
    }
}

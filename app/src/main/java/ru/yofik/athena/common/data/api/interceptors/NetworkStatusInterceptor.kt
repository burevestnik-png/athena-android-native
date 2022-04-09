package ru.yofik.athena.common.data.api.interceptors

import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response
import ru.yofik.athena.common.data.api.ConnectionManager
import ru.yofik.athena.common.domain.model.NetworkUnavailableException

class NetworkStatusInterceptor
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

package ru.yofik.athena.common.data.api.common.interceptors

import javax.inject.Inject
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

class LoggingInterceptor @Inject constructor() : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        Timber.tag("api").d(message)
    }
}

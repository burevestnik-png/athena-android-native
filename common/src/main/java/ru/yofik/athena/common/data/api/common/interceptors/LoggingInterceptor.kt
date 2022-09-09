package ru.yofik.athena.common.data.api.common.interceptors

import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import javax.inject.Inject

class LoggingInterceptor @Inject constructor() : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        Timber.tag("api").d(message)
    }
}

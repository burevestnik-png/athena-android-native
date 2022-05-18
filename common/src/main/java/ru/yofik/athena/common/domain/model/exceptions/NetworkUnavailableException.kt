package ru.yofik.athena.common.domain.model.exceptions

import java.io.IOException

/** Exception which is used in interceptor to signalize about network unavailability */
class NetworkUnavailableException(message: String = "No network available") : IOException(message)

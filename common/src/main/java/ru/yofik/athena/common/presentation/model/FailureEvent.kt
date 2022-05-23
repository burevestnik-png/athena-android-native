package ru.yofik.athena.common.presentation.model

/**
 * Wrapper class for presentation layer. Due to one way flow of data in presentation and preventing
 * handling same error several times this wrapper was made. When fragment is trying to handle error,
 * it is using [getFailureOrNull] and if error was handled this method will return null
 */
data class FailureEvent(private val failure: Throwable) {
    private var isHandled = false

    fun getFailureOrNull(): Throwable? {
        return if (isHandled) {
            null
        } else {
            isHandled = true
            failure
        }
    }
}

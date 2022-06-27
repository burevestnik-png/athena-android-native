package ru.yofik.athena.common.presentation.model

/**
 * Wrapper class for presentation layer. Due to one way flow of data in presentation and preventing
 * handling same error several times this wrapper was made. When fragment is trying to handle error,
 * it is using [getPayloadOrNull] and if error was handled this method will return null
 */
data class Event<Payload>(private val payload: Payload) {
    private var isHandled = false

    fun getPayloadOrNull(): Payload? =
        if (isHandled) {
            null
        } else {
            isHandled = true
            payload
        }
}

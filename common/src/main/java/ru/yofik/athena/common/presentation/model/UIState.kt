package ru.yofik.athena.common.presentation.model

class EmptyPayload

data class UIState<Payload>(
    val payload: Payload,
    val loading: Boolean = false,
    val failure: Event<Throwable>? = null
) {
    fun copy(
        loading: Boolean? = null,
        failure: Event<Throwable>? = null,
        copyPayload: (Payload) -> Payload = { it }
    ): UIState<Payload> {
        return copy(
            loading = loading ?: this.loading,
            failure = failure ?: this.failure,
            payload = copyPayload(payload)
        )
    }
}

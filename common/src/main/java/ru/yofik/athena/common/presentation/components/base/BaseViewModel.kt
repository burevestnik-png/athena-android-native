package ru.yofik.athena.common.presentation.components.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.yofik.athena.common.presentation.components.extensions.createExceptionHandler
import ru.yofik.athena.common.presentation.model.FailureEvent
import ru.yofik.athena.common.presentation.model.UIState
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<Payload>(payload: Payload) : ViewModel() {
    @Suppress("FunctionName")
    protected fun MutableUIStateFlow(payload: Payload) = MutableStateFlow(UIState(payload))

    protected val _state = MutableUIStateFlow(payload)
    val state: StateFlow<UIState<Payload>> = _state

    protected val payload
        get() = state.value.payload

    protected fun modifyState(
        loading: Boolean = _state.value.loading,
        failure: FailureEvent? = _state.value.failure,
        copyPayload: (Payload) -> Payload = { it }
    ) {
        _state.value =
            state.value.copy(loading = loading, failure = failure, copyPayload = copyPayload)
    }

    protected fun showLoader() {
        _state.value = state.value.copy(loading = true)
    }

    protected fun hideLoader() {
        _state.value = state.value.copy(loading = false)
    }

    protected fun launchIORequest(
        coroutineContext: CoroutineContext = Dispatchers.IO,
        errorMessage: String = "Failed to make request",
        request: suspend () -> Unit
    ) {
        val exceptionHandler = viewModelScope.createExceptionHandler(errorMessage) { onFailure(it) }
        viewModelScope.launch(exceptionHandler + coroutineContext) { request() }
    }

    protected abstract fun onFailure(throwable: Throwable)
}

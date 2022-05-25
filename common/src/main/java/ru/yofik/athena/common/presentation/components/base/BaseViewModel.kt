package ru.yofik.athena.common.presentation.components.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.yofik.athena.common.presentation.components.extensions.createExceptionHandler
import ru.yofik.athena.common.presentation.model.UIState

abstract class BaseViewModel : ViewModel() {

    @Suppress("FunctionName")
    protected fun <Payload> MutableUIStateFlow(payload: Payload) =
        MutableStateFlow(UIState(payload))

    protected fun <Payload> showLoader(state: StateFlow<UIState<Payload>>) =
        state.value.copy(loading = true)

    protected fun <Payload> hideLoader(state: StateFlow<UIState<Payload>>) =
        state.value.copy(loading = false)

    protected fun launchApiRequest(
        errorMessage: String = "Failed to make request",
        request: suspend () -> Unit
    ) {
        val exceptionHandler = viewModelScope.createExceptionHandler(errorMessage) { onFailure(it) }
        viewModelScope.launch(exceptionHandler) { request() }
    }

    protected abstract fun onFailure(throwable: Throwable)
}

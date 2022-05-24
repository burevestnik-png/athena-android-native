package ru.yofik.athena.createchat.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import ru.yofik.athena.common.presentation.model.FailureEvent
import ru.yofik.athena.createchat.domain.model.UiUser

sealed class UIState {
    object Idle : UIState()
    object Loading : UIState()
    data class Failure(val failure: FailureEvent) : UIState()
}

abstract class BaseViewModel : ViewModel() {
    @Suppress("FunctionName")
    protected fun MutableUIStateFlow() = MutableStateFlow<UIState>(UIState.Idle)
}

// todo rename CreateChatViewState
data class CreateChatState(
    val noMoreUsersAnymore: Boolean = false,
    val users: List<UiUser> = emptyList()
) : UIState()

data class CreateChatViewState(
    val loading: Boolean = false,
    val failure: FailureEvent? = null,
)

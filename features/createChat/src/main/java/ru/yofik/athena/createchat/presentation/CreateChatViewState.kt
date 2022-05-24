package ru.yofik.athena.createchat.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import ru.yofik.athena.common.presentation.model.FailureEvent
import ru.yofik.athena.createchat.domain.model.UiUser

abstract class BaseViewModel : ViewModel() {

}


open class UIState(
    val loading: Boolean = false,
    val failure: FailureEvent? = null,
)

// todo rename CreateChatViewState
//data class CreateChatState() : UIState<>()

data class CreateChatViewState(
//    val loading: Boolean = false,
//    val failure: FailureEvent? = null,
    val noMoreUsersAnymore: Boolean = false,
    val users: List<UiUser> = emptyList()
) : UIState()

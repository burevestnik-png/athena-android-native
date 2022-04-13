package ru.yofik.athena.login.presentation

import ru.yofik.athena.common.presentation.FailureEvent

data class LoginViewState(
    val loading: Boolean = false,
    val failure: FailureEvent? = null,
    val shouldNavigateToChatListScreen: Boolean = false
)

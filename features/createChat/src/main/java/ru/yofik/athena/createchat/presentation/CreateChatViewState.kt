package ru.yofik.athena.createchat.presentation

import ru.yofik.athena.common.presentation.FailureEvent
import ru.yofik.athena.common.presentation.model.user.UiUser

data class CreateChatViewState(
    val loading: Boolean = false,
    val failure: FailureEvent? = null,
    val users: List<UiUser> = emptyList()
)

package ru.yofik.athena.createchat.presentation

import ru.yofik.athena.common.presentation.model.FailureEvent
import ru.yofik.athena.createchat.domain.model.UiUser

data class CreateChatViewState(
    val loading: Boolean = false,
    val failure: FailureEvent? = null,
    val noMoreUsersAnymore: Boolean = false,
    val users: List<UiUser> = emptyList()
)

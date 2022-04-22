package ru.yofik.athena.chat.presentation

import ru.yofik.athena.chat.domain.model.UiMessage
import ru.yofik.athena.common.presentation.FailureEvent

data class ChatFragmentState(
    val loading: Boolean = false,
    val id: Long = -1,
    val messages: List<UiMessage> = emptyList(),
    val failure: FailureEvent? = null
)

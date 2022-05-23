package ru.yofik.athena.chat.presentation

import ru.yofik.athena.chat.domain.model.UiMessage
import ru.yofik.athena.common.presentation.model.FailureEvent

data class ChatFragmentState(
    val loading: Boolean = false,
    val messages: List<UiMessage> = emptyList(),
    val input: String = "",
    val failure: FailureEvent? = null
)

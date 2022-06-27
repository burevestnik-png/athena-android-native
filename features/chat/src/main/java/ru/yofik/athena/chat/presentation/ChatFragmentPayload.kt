package ru.yofik.athena.chat.presentation

import ru.yofik.athena.chat.domain.model.UiMessage

data class ChatFragmentPayload(
    val messages: List<UiMessage> = emptyList(),
    val input: String = "",
    val noMoreMessagesAvailable: Boolean = false
)

package ru.yofik.athena.chatlist.presentation

import ru.yofik.athena.common.domain.model.chat.Chat
import ru.yofik.athena.common.presentation.FailureEvent

data class ChatListViewState(
    val loading: Boolean = false,
    val chats: List<Chat> = emptyList(),
    val failure: FailureEvent? = null
)

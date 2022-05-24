package ru.yofik.athena.chatlist.presentation

import ru.yofik.athena.chatlist.domain.model.UiChat
import ru.yofik.athena.common.presentation.model.FailureEvent



data class ChatListViewState(
    val loading: Boolean = false,
    val noMoreChatsAnymore: Boolean = false,
    val chats: List<UiChat> = emptyList(),
    val failure: FailureEvent? = null
)

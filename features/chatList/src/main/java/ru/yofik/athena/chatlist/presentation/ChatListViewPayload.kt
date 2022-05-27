package ru.yofik.athena.chatlist.presentation

import ru.yofik.athena.chatlist.domain.model.UiChat

data class ChatListViewPayload(
    val noMoreChatsAnymore: Boolean = false,
    val chats: List<UiChat> = emptyList(),
)

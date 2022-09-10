package ru.yofik.athena.chatlist.presentation

import ru.yofik.athena.chatlist.domain.model.UiChat

data class ChatListFragmentPayload(
    val mode: Mode = Mode.DEFAULT,
    val noMoreChatsAnymore: Boolean = false,
    val chats: List<UiChat> = emptyList()
) {
    sealed class Mode {
        object DEFAULT : Mode()
        object SELECTION : Mode()
    }
}

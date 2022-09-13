package ru.yofik.athena.chatlist.presentation

import ru.yofik.athena.chatlist.domain.model.UiChat

data class ChatListFragmentPayload(
    val mode: Mode = Mode.DEFAULT,
    val noMoreChatsAnymore: Boolean = false,
    val chats: List<UiChat> = emptyList()
) {
    val selectedChatsAmount: Int
        get() = chats.count { it.isSelected }

    sealed class Mode {
        object DEFAULT : Mode() {
            override fun toString() = DEFAULT::class.simpleName!!
        }

        object SELECTION : Mode() {
            override fun toString() = SELECTION::class.simpleName!!
        }
    }
}

fun ChatListFragmentPayload.toggleChatSelection(id: Long): List<UiChat> =
    this.chats.map { chat -> if (chat.id == id) chat.copy(isSelected = !chat.isSelected) else chat }

package ru.yofik.athena.createchat.presentation

import ru.yofik.athena.common.presentation.model.user.UiUser

sealed class CreateChatEvent {
    object GetAllUsers : CreateChatEvent()
    data class CreateChat(val with: UiUser) : CreateChatEvent()
}

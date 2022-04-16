package ru.yofik.athena.createChat.presentation

import ru.yofik.athena.common.domain.model.user.User
import ru.yofik.athena.common.presentation.model.user.UiUser

sealed class CreateChatEvent {
    object GetAllUsers : CreateChatEvent()
    data class CreateChat(val with: UiUser) : CreateChatEvent()
}

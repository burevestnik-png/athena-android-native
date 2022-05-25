package ru.yofik.athena.createchat.presentation

import ru.yofik.athena.createchat.domain.model.UiUser

data class CreateChatStatePayload(
    val noMoreUsersAnymore: Boolean = false,
    val users: List<UiUser> = emptyList()
)

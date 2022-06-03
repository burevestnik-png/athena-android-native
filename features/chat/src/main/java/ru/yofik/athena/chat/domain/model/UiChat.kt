package ru.yofik.athena.chat.domain.model

import ru.yofik.athena.common.domain.model.users.User

data class UiChat(
    val id: Long,
    val name: String,
    val users: List<User>,
    val chatHolderId: Long
)
package ru.yofik.athena.chat.domain.model

import ru.yofik.athena.common.domain.model.user.User

data class UiChat(
    val id: Long,
    val name: String,
    val users: List<User>
)
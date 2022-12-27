package ru.yofik.athena.chat.domain.model

import ru.yofik.athena.common.domain.model.users.UserV2

data class UiChat(val id: Long, val name: String, val users: List<UserV2>, val chatHolderId: Long)

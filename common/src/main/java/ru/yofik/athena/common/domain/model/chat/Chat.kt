package ru.yofik.athena.common.domain.model.chat

import ru.yofik.athena.common.domain.model.message.Message
import ru.yofik.athena.common.domain.model.user.User

data class Chat(val id: Long, val name: String, val users: List<User>, val lastMessage: Message)

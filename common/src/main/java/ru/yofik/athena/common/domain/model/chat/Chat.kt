package ru.yofik.athena.common.domain.model.chat

import ru.yofik.athena.common.domain.model.message.Message
import ru.yofik.athena.common.domain.model.user.User

data class Chat(val id: Long, val name: String, val users: List<User>, val lastMessage: Message) {
    val isEmpty: Boolean
        get() = lastMessage.isNullable

    companion object {
        fun empty(id: Long, name: String, users: List<User>): Chat {
            return Chat(id, name, users, Message.nullable())
        }
    }
}

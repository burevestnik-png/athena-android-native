package ru.yofik.athena.common.domain.model.chat

import ru.yofik.athena.common.domain.model.message.Message
import ru.yofik.athena.common.domain.model.user.User

enum class ChatType {
    PERSONAL,
    GROUP
}

data class Chat(
    val id: Long,
    val type: ChatType,
    val name: String,
    val users: List<User>,
    val lastMessage: Message
) {
    val isEmpty: Boolean
        get() = lastMessage.isNullable

    companion object {
        fun empty(id: Long, type: ChatType, name: String, users: List<User>): Chat {
            return Chat(id, type, name, users, Message.nullable())
        }
    }
}

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

    val nullable: Boolean
        get() = id == NULLABLE_ID

    companion object {
        private const val NULLABLE_ID = -1L

        fun empty(id: Long, type: ChatType, name: String, users: List<User>): Chat {
            return Chat(
                id = id,
                type = type,
                name = name,
                users = users,
                lastMessage = Message.nullable()
            )
        }

        fun nullable(): Chat {
            return empty(id = NULLABLE_ID, type = ChatType.PERSONAL, name = "", users = emptyList())
        }
    }
}

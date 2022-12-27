package ru.yofik.athena.common.domain.model.chat

import ru.yofik.athena.common.domain.model.message.Message
import ru.yofik.athena.common.domain.model.users.UserV2

enum class ChatType {
    PERSONAL,
    GROUP
}

data class Chat(
    val id: Long,
    val type: ChatType,
    val name: String,
    val users: List<UserV2>,
    val lastMessage: Message
) {
    val isEmpty: Boolean
        get() = lastMessage.isNullable

    val isNullable: Boolean
        get() = id == NULLABLE_ID

    companion object {
        private const val NULLABLE_ID = -1L

        fun empty(id: Long, type: ChatType, name: String, users: List<UserV2>): Chat {
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

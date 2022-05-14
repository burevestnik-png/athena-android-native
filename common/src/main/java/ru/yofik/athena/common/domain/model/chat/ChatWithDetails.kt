package ru.yofik.athena.common.domain.model.chat

import ru.yofik.athena.common.domain.model.message.Message
import ru.yofik.athena.common.domain.model.user.User

/** Basic entity object which has more details than base entity Chat */
data class ChatWithDetails(
    val id: Long,
    val name: String,
    val users: List<User>,
    val messages: List<Message>
) {
    val isNullable
        get() = id == NULLABLE_ID

    companion object {
        private const val NULLABLE_ID = -1L

        fun nullable(): ChatWithDetails {
            return ChatWithDetails(
                id = NULLABLE_ID,
                name = "",
                users = emptyList(),
                messages = emptyList()
            )
        }
    }
}

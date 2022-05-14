package ru.yofik.athena.common.domain.model.message

import org.threeten.bp.LocalDateTime

data class Message(
    val id: Long,
    val content: String,
    val senderId: Long,
    val chatId: Long,
    val creationDate: LocalDateTime,
    val modificationDate: LocalDateTime
) {
    val isNullable
        get() = id == NULLABLE_ID

    companion object {
        private const val NULLABLE_ID = -1L

        fun nullable(): Message {
            return Message(
                id = NULLABLE_ID,
                content = "",
                senderId = NULLABLE_ID,
                chatId = NULLABLE_ID,
                creationDate = LocalDateTime.now(),
                modificationDate = LocalDateTime.now()
            )
        }
    }
}

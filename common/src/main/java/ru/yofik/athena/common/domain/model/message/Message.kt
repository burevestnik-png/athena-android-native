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
    companion object {
        const val NULLABLE_MESSAGE_ID = -1L
    }
}

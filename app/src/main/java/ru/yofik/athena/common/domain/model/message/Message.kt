package ru.yofik.athena.common.domain.model.message

import org.threeten.bp.LocalDateTime

data class Message(
    val id: Long,
    val content: String,
    val senderId: Long,
    val chatId: Long,
    val dateTime: LocalDateTime
)

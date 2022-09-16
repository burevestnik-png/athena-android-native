package ru.yofik.athena.chat.domain.model

enum class UiMessageSenderType {
    OWNER,
    NOT_OWNER
}

data class UiMessage(
    val id: Long,
    val sender: String,
    val content: String,
    val time: String,
    val senderType: UiMessageSenderType
)

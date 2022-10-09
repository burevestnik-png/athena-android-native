package ru.yofik.athena.chat.domain.model

import org.threeten.bp.LocalDateTime
import ru.yofik.athena.common.utils.TimeUtils

enum class UiMessageSenderType {
    OWNER,
    NOT_OWNER
}

data class UiMessage(
    val id: Long,
    val sender: String,
    val content: String,
    val dateTime: LocalDateTime,
    val senderType: UiMessageSenderType
) {
    val time: String
        get() = TimeUtils.localDateTimeToString(this.dateTime, format = TimeUtils.SHORT_TIME_FORMAT)
}

package ru.yofik.athena.common.domain.model.notification

import ru.yofik.athena.common.domain.model.message.Message

data class UpdateMessageNotification(
    val message: Message
) : Notification(NotificationType.UPDATED_MESSAGE)
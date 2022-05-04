package ru.yofik.athena.common.domain.model.notification

import ru.yofik.athena.common.domain.model.message.Message

data class NewMessageNotification(val message: Message) :
    Notification(NotificationType.NEW_MESSAGE)

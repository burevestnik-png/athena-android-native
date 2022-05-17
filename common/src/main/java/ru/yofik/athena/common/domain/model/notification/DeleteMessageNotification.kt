package ru.yofik.athena.common.domain.model.notification

data class DeleteMessageNotification(val chatId: Long, val ids: List<Long>) :
    Notification(NotificationType.DELETED_MESSAGES)

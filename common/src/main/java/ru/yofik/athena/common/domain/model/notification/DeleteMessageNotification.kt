package ru.yofik.athena.common.domain.model.notification

data class DeleteMessageNotification(
    val ids: List<Int>
) : Notification(NotificationType.DELETED_MESSAGES)

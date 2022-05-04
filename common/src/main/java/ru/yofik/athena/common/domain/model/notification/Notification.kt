package ru.yofik.athena.common.domain.model.notification

enum class NotificationType {
    NEW_MESSAGE,
    UPDATED_MESSAGE,
    DELETED_MESSAGES
}

abstract class Notification(val type: NotificationType)
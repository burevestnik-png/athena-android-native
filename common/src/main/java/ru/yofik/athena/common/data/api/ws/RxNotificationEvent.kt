package ru.yofik.athena.common.data.api.ws

import ru.yofik.athena.common.domain.model.notification.Notification


sealed class RxNotificationEvent {
    data class NewNotification(val notification: Notification) : RxNotificationEvent()
}

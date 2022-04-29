package ru.yofik.athena.common.data.api.ws

import ru.yofik.athena.common.domain.model.message.Message

sealed class RxNotificationEvent {
    data class Notification(val message: Message)
}

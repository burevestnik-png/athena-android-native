package ru.yofik.athena.chatlist.domain.usecases

import ru.yofik.athena.common.data.api.ws.RxNotificationEvent
import ru.yofik.athena.common.data.api.ws.RxNotificationPublisher
import javax.inject.Inject

class SubscribeOnNotifications @Inject constructor(){
    operator fun invoke() {
        return RxNotificationPublisher.listen(RxNotificationEvent.Notification::class.java)
    }
}
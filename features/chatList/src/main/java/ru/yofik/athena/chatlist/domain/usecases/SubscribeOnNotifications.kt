package ru.yofik.athena.chatlist.domain.usecases

import io.reactivex.Observable
import ru.yofik.athena.common.data.api.ws.RxNotificationEvent
import ru.yofik.athena.common.data.api.ws.RxNotificationPublisher
import ru.yofik.athena.common.domain.model.notification.MessageNotification
import ru.yofik.athena.common.domain.repositories.NotificationRepository
import javax.inject.Inject

class SubscribeOnNotifications @Inject constructor(
    private val notificationRepository: NotificationRepository
){
    operator fun invoke(): Observable<MessageNotification> {
        return notificationRepository.subscribeOnNotifications()
    }
}
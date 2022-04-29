package ru.yofik.athena.common.data.repositories

import io.reactivex.Observable
import javax.inject.Inject
import okhttp3.WebSocket
import ru.yofik.athena.common.data.api.ws.RxNotificationEvent
import ru.yofik.athena.common.data.api.ws.RxNotificationPublisher
import ru.yofik.athena.common.data.api.ws.model.mappers.SubscribeNotificationMapper
import ru.yofik.athena.common.data.api.ws.model.notifications.SubscribeNotification
import ru.yofik.athena.common.domain.model.notification.MessageNotification
import ru.yofik.athena.common.domain.repositories.NotificationRepository

class NotificationRepositoryImpl
@Inject
constructor(
    private val notificationWebSocket: WebSocket,
    private val subscribeNotificationMapper: SubscribeNotificationMapper
) : NotificationRepository {
    override fun startNotificationChannel() {
        notificationWebSocket.send(
            subscribeNotificationMapper.mapToApi(SubscribeNotification.build())
        )
    }

    override fun subscribeOnNotifications(): Observable<MessageNotification> {
        return RxNotificationPublisher.listen(RxNotificationEvent.Notification::class.java).map {
            MessageNotification(message = it.message)
        }
    }

    override fun subscribeOnTargetChatNotifications(chatId: Long): Observable<MessageNotification> {
        return subscribeOnNotifications().filter { it.message.chatId == chatId }
    }
}

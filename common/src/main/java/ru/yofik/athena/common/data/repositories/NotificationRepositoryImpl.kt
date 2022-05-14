package ru.yofik.athena.common.data.repositories

import io.reactivex.Observable
import okhttp3.WebSocket
import ru.yofik.athena.common.data.api.ws.RxNotificationEvent
import ru.yofik.athena.common.data.api.ws.RxNotificationPublisher
import ru.yofik.athena.common.data.api.ws.model.messages.ApiSubscribeOnNotificationsMessage
import ru.yofik.athena.common.domain.model.notification.DeleteMessageNotification
import ru.yofik.athena.common.domain.model.notification.NewMessageNotification
import ru.yofik.athena.common.domain.model.notification.NotificationType
import ru.yofik.athena.common.domain.model.notification.UpdateMessageNotification
import ru.yofik.athena.common.domain.repositories.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl
@Inject constructor(
    private val notificationWebSocket: WebSocket,
) : NotificationRepository {
    private val notificationStream =
        RxNotificationPublisher.listen(RxNotificationEvent.NewNotification::class.java)

    override fun startNotificationChannel() {
        val initialMessage = ApiSubscribeOnNotificationsMessage.toJson()
        notificationWebSocket.send(
            initialMessage
        )
    }

    override fun subscribeOnDeletedMessagesNotifications(): Observable<DeleteMessageNotification> {
        TODO("Not yet implemented")
    }

    override fun subscribeOnUpdatedMessageNotifications(): Observable<UpdateMessageNotification> {
        TODO("Not yet implemented")
    }

    override fun subscribeOnNewMessageNotifications(): Observable<NewMessageNotification> {
        return notificationStream.filter { it.notification.type == NotificationType.NEW_MESSAGE }
            .map { NewMessageNotification(message = (it.notification as NewMessageNotification).message) }
    }

    override fun subscribeOnTargetChatNewMessageNotifications(chatId: Long): Observable<NewMessageNotification> {
        return subscribeOnNewMessageNotifications()
            .filter { it.message.chatId == chatId }
    }
}

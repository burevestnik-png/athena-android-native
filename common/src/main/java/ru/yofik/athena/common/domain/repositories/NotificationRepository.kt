package ru.yofik.athena.common.domain.repositories

import io.reactivex.Observable
import ru.yofik.athena.common.domain.model.notification.DeleteMessageNotification
import ru.yofik.athena.common.domain.model.notification.NewMessageNotification
import ru.yofik.athena.common.domain.model.notification.UpdateMessageNotification

interface NotificationRepository {
    fun subscribeOnNotificationWebsocket()

    fun listenNewMessageNotifications(): Observable<NewMessageNotification>
    fun listenTargetChatNewMessageNotifications(
        chatId: Long
    ): Observable<NewMessageNotification>

    fun listenUpdatedMessageNotifications(): Observable<UpdateMessageNotification>
    fun listenDeletedMessagesNotifications(): Observable<DeleteMessageNotification>
}

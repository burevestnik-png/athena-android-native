package ru.yofik.athena.common.domain.repositories

import io.reactivex.Observable
import ru.yofik.athena.common.domain.model.notification.DeleteMessageNotification
import ru.yofik.athena.common.domain.model.notification.NewMessageNotification
import ru.yofik.athena.common.domain.model.notification.UpdateMessageNotification

interface NotificationRepository {
    fun startNotificationChannel()

    fun subscribeOnNewMessageNotifications(): Observable<NewMessageNotification>
    fun subscribeOnTargetChatNewMessageNotifications(
        chatId: Long
    ): Observable<NewMessageNotification>

    fun subscribeOnUpdatedMessageNotifications(): Observable<UpdateMessageNotification>
    fun subscribeOnDeletedMessagesNotifications(): Observable<DeleteMessageNotification>
}

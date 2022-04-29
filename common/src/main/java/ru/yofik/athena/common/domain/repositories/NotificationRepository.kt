package ru.yofik.athena.common.domain.repositories

import io.reactivex.Observable
import ru.yofik.athena.common.domain.model.notification.MessageNotification

interface NotificationRepository {
    fun startNotificationChannel()

    fun subscribeOnNotifications(): Observable<MessageNotification>
    fun subscribeOnTargetChatNotifications(chatId: Long): Observable<MessageNotification>
}
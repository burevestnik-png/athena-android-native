package ru.yofik.athena.common.domain.repositories

import io.reactivex.Observable

interface NotificationRepository {
    fun startNotificationChannel()

//    fun subscribeOnNewMessageNotifications(): Observable<MessageNotification>
//    fun subscribeOnTargetChatNotifications(chatId: Long): Observable<MessageNotification>
}
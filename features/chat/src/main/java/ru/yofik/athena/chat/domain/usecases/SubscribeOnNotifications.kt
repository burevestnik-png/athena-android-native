package ru.yofik.athena.chat.domain.usecases

import io.reactivex.Observable
import ru.yofik.athena.common.domain.model.notification.MessageNotification
import ru.yofik.athena.common.domain.repositories.NotificationRepository
import javax.inject.Inject

class SubscribeOnNotifications @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    operator fun invoke(chatId: Long): Observable<MessageNotification> {
        return notificationRepository.subscribeOnTargetChatNotifications(chatId)
    }
}
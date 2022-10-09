package ru.yofik.athena.chat.domain.usecases

import io.reactivex.Observable
import ru.yofik.athena.common.domain.model.notification.NewMessageNotification
import ru.yofik.athena.common.domain.repositories.NotificationRepository
import javax.inject.Inject

class SubscribeOnNewMessageNotifications
@Inject
constructor(private val notificationRepository: NotificationRepository) {
    operator fun invoke(chatId: Long): Observable<NewMessageNotification> {
        return notificationRepository.listenTargetChatNewMessageNotifications(chatId)
    }
}

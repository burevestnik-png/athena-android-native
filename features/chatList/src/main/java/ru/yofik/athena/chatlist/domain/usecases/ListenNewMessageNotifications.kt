package ru.yofik.athena.chatlist.domain.usecases

import io.reactivex.Observable
import ru.yofik.athena.common.domain.model.notification.NewMessageNotification
import ru.yofik.athena.common.domain.repositories.NotificationRepository
import javax.inject.Inject

class ListenNewMessageNotifications
@Inject
constructor(private val notificationRepository: NotificationRepository) {
    operator fun invoke(): Observable<NewMessageNotification> {
        return notificationRepository.listenNewMessageNotifications()
    }
}

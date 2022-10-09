package ru.yofik.athena.chatlist.domain.usecases

import ru.yofik.athena.common.domain.repositories.NotificationRepository
import javax.inject.Inject

class SubscribeOnNotifications
@Inject
constructor(private val notificationRepository: NotificationRepository) {
    operator fun invoke() {
        notificationRepository.subscribeOnNotificationWebsocket()
    }
}

package ru.yofik.athena.chat.domain.usecases

import javax.inject.Inject
import ru.yofik.athena.common.domain.model.notification.NewMessageNotification
import ru.yofik.athena.common.domain.repositories.MessageRepository

class HandleNewMessage @Inject constructor(private val messageRepository: MessageRepository) {
    suspend operator fun invoke(notification: NewMessageNotification) {
        messageRepository.cacheMessage(notification.message)
    }
}

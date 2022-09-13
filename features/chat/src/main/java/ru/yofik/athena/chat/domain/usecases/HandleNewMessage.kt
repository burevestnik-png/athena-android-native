package ru.yofik.athena.chat.domain.usecases

import ru.yofik.athena.common.domain.model.notification.NewMessageNotification
import ru.yofik.athena.common.domain.repositories.ChatRepository
import ru.yofik.athena.common.domain.repositories.MessageRepository
import javax.inject.Inject

class HandleNewMessage @Inject constructor(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(notification: NewMessageNotification) {
        chatRepository.updateLastMessage(notification.message)
    }
}

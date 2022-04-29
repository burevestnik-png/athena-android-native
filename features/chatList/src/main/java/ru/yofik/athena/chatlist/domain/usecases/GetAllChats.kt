package ru.yofik.athena.chatlist.domain.usecases

import ru.yofik.athena.common.domain.model.chat.Chat
import ru.yofik.athena.common.domain.repositories.ChatRepository
import ru.yofik.athena.common.domain.repositories.NotificationRepository
import javax.inject.Inject

class GetAllChats @Inject constructor(
    private val chatRepository: ChatRepository,
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(): List<Chat> {
        notificationRepository.startNotificationChannel()
        return chatRepository.requestGetAllChats()
    }
}

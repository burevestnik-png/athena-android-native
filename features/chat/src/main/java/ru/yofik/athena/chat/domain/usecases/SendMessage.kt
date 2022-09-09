package ru.yofik.athena.chat.domain.usecases

import ru.yofik.athena.common.domain.repositories.MessageRepository
import javax.inject.Inject

class SendMessage @Inject constructor(
    private val messageRepository: MessageRepository
) {
    suspend operator fun invoke(chatId: Long, text: String) {
        messageRepository.requestSendMessage(chatId, text)
    }
}
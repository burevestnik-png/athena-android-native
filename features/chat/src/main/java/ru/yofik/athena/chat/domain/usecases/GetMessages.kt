package ru.yofik.athena.chat.domain.usecases

import ru.yofik.athena.common.domain.repositories.MessageRepository
import javax.inject.Inject

class GetMessages @Inject constructor(private val messageRepository: MessageRepository) {
    operator fun invoke(chatId: Long) = messageRepository.getCachedMessagesForDefiniteChat(chatId)
}

package ru.yofik.athena.chat.domain.usecases

import javax.inject.Inject
import ru.yofik.athena.common.domain.repositories.MessageRepository

class GetMessages @Inject constructor(private val messageRepository: MessageRepository) {
    operator fun invoke(chatId: Long) = messageRepository.getCachedMessagesForDefiniteChat(chatId)
}

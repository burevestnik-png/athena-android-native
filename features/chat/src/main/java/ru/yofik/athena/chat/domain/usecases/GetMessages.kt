package ru.yofik.athena.chat.domain.usecases

import kotlinx.coroutines.flow.Flow
import ru.yofik.athena.common.domain.model.message.Message
import ru.yofik.athena.common.domain.repositories.MessageRepository
import javax.inject.Inject

class GetMessages @Inject constructor(private val messageRepository: MessageRepository) {
    suspend operator fun invoke(chatId: Long): Flow<List<Message>> {
        // add last message as LastMessage
        messageRepository.removeAllCachedMessagesByChatId(chatId)
        return messageRepository.getCachedMessagesByChatId(chatId)
    }
}

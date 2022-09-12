package ru.yofik.athena.chatlist.domain.usecases

import ru.yofik.athena.common.domain.model.message.Message
import ru.yofik.athena.common.domain.repositories.ChatRepository
import ru.yofik.athena.common.domain.repositories.MessageRepository
import javax.inject.Inject

class UpdateMessage @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(message: Message) {
        chatRepository.updateLastMessage(message)
    }
}
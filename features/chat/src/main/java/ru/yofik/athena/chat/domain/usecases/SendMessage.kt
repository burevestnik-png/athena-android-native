package ru.yofik.athena.chat.domain.usecases

import ru.yofik.athena.common.domain.repositories.ChatRepository
import javax.inject.Inject

class SendMessage @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(chatId: Long, text: String) {
        chatRepository.requestSendMessage(chatId, text)
    }
}
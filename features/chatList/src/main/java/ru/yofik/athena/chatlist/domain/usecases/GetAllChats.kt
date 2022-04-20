package ru.yofik.athena.chatlist.domain.usecases

import ru.yofik.athena.common.domain.model.chat.Chat
import ru.yofik.athena.common.domain.repositories.ChatRepository
import javax.inject.Inject

class GetAllChats @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(): List<Chat> {
        return chatRepository.requestGetAllChats()
    }
}
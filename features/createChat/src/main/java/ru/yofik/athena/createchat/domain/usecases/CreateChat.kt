package ru.yofik.athena.createchat.domain.usecases

import ru.yofik.athena.common.domain.model.chat.Chat
import javax.inject.Inject
import ru.yofik.athena.common.domain.repositories.ChatRepository

class CreateChat @Inject constructor(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(name: String, id: Long): Chat {
        return chatRepository.requestCreateChat(name, id)
    }
}
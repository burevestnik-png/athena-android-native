package ru.yofik.athena.createchat.domain.usecases

import javax.inject.Inject
import ru.yofik.athena.common.domain.model.chat.Chat
import ru.yofik.athena.common.domain.repositories.ChatRepository

class CreateChat @Inject constructor(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(chatId: Long, name: String): Chat {
        //        val chats = chatRepository.getCachedChatts().toList()
        // todo check api request
        //        val chats = chatRepository.requestGetPaginatedChats()
        //        chats.forEach { if (it.id == id) throw ChatAlreadyCreatedException() }

        return chatRepository.requestCreateChat(name, chatId)
    }
}

package ru.yofik.athena.createchat.domain.usecases

import ru.yofik.athena.common.domain.model.chat.Chat
import ru.yofik.athena.common.domain.repositories.ChatRepository
import javax.inject.Inject

class CreateChat @Inject constructor(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(targetUserId: Long): Chat {
        //        val chats = chatRepository.getCachedChatts().toList()
        // todo check api request
        //        val chats = chatRepository.requestGetPaginatedChats()
        //        chats.forEach { if (it.id == id) throw ChatAlreadyCreatedException() }

        return chatRepository.requestCreateChat(targetUserId)
    }
}

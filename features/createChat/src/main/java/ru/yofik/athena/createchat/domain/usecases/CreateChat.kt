package ru.yofik.athena.createchat.domain.usecases

import javax.inject.Inject
import ru.yofik.athena.common.domain.model.chat.Chat
import ru.yofik.athena.common.domain.repositories.ChatRepository
import ru.yofik.athena.createchat.domain.model.exceptions.ChatAlreadyCreatedException

class CreateChat @Inject constructor(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(name: String, id: Long): Chat {
        // todo rewrite on cache
//        val chats = chatRepository.requestGetPaginatedChats()
//        chats.forEach { if (it.id == id) throw ChatAlreadyCreatedException() }

        return chatRepository.requestCreateChat(name, id)
    }
}

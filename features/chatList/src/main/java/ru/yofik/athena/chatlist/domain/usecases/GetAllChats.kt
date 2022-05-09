package ru.yofik.athena.chatlist.domain.usecases

import ru.yofik.athena.common.domain.model.chat.Chat
import ru.yofik.athena.common.domain.repositories.ChatRepository
import ru.yofik.athena.common.domain.repositories.UserRepository
import javax.inject.Inject

class GetAllChats @Inject constructor(
    private val chatRepository: ChatRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): List<Chat> {
        // todo rework crutch
        val chats = chatRepository.requestGetAllChats()
        val currentUserName = userRepository.getCachedUser().name

        return chats.map { chat ->
            val companion = chat.users.first { it.name != currentUserName }.name
            if (chat.name == companion) {
                chat
            } else {
                chat.copy(name = companion)
            }
        }
    }
}

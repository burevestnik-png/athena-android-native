package ru.yofik.athena.chat.domain.usecases

import ru.yofik.athena.common.domain.model.chat.ChatWithDetails
import ru.yofik.athena.common.domain.repositories.ChatRepository
import ru.yofik.athena.common.domain.repositories.UserRepository
import javax.inject.Inject

class GetChat @Inject constructor(
    private val chatRepository: ChatRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(id: Long): ChatWithDetails {
        // todo rework crutch
        val chat = chatRepository.requestGetChat(id)
        val curUserName = userRepository.getCachedUser().name
        val opponentName = chat.users.first { it.name != curUserName }.name
        return if (chat.name != opponentName) {
            chat.copy(name = opponentName)
        } else {
            chat
        }
    }
}

package ru.yofik.athena.chat.domain.usecases

import ru.yofik.athena.common.domain.model.chat.Chat
import ru.yofik.athena.common.domain.repositories.ChatRepository
import javax.inject.Inject

class GetChat
@Inject
constructor(
    private val chatRepository: ChatRepository,
) {
    suspend operator fun invoke(id: Long): Chat = chatRepository.getCachedChat(id)
}

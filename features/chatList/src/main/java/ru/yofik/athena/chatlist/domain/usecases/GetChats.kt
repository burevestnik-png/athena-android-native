package ru.yofik.athena.chatlist.domain.usecases

import ru.yofik.athena.common.domain.repositories.ChatRepository
import javax.inject.Inject

class GetChats @Inject constructor(private val chatRepository: ChatRepository) {
    operator fun invoke() = chatRepository.getCachedChats()
}

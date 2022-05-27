package ru.yofik.athena.chatlist.domain.usecases

import ru.yofik.athena.common.domain.repositories.ChatRepository
import javax.inject.Inject
import ru.yofik.athena.common.domain.repositories.UserRepository

class ForceRefreshChats @Inject constructor(private val chatRepository: ChatRepository) {
    suspend operator fun invoke() {
        chatRepository.removeCachedChats()
    }
}

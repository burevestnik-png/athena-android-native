package ru.yofik.athena.chatlist.domain.usecases

import ru.yofik.athena.common.domain.repositories.ChatRepository
import javax.inject.Inject

class RemoveChatCache @Inject constructor(private val chatRepository: ChatRepository) {
    suspend operator fun invoke() {
        chatRepository.removeAllCache()
    }
}

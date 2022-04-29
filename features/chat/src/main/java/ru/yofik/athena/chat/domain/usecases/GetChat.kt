package ru.yofik.athena.chat.domain.usecases

import javax.inject.Inject
import ru.yofik.athena.common.domain.model.chat.ChatWithDetails
import ru.yofik.athena.common.domain.repositories.ChatRepository
import ru.yofik.athena.common.domain.repositories.NotificationRepository

class GetChat @Inject constructor(
    private val chatRepository: ChatRepository,
) {
    suspend operator fun invoke(id: Long): ChatWithDetails {
        return chatRepository.requestGetChat(id)
    }
}

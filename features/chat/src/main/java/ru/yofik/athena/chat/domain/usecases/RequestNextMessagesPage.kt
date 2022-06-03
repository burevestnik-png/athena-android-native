package ru.yofik.athena.chat.domain.usecases

import javax.inject.Inject
import ru.yofik.athena.common.domain.model.pagination.Pagination
import ru.yofik.athena.common.domain.repositories.MessageRepository

class RequestNextMessagesPage
@Inject
constructor(private val messageRepository: MessageRepository) {
    suspend operator fun invoke(
        chatId: Long,
        pageNumber: Int,
        pageSize: Int = Pagination.DEFAULT_PAGE_SIZE
    ) {
        val (messages, pagination) =
            messageRepository.requestGetPaginatedMessages(chatId, pageNumber, pageSize)


    }
}

package ru.yofik.athena.chat.domain.usecases

import ru.yofik.athena.common.domain.model.exceptions.NoMoreItemsException
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
    ): Pagination {
        val (messages, pagination) =
            messageRepository.requestGetPaginatedMessages(chatId, pageNumber, pageSize)

        messageRepository.cacheMessages(messages)

        if (!pagination.canLoadMore) {
            throw NoMoreItemsException("No more messages available")
        }

        return pagination
    }
}

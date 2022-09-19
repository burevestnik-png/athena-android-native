package ru.yofik.athena.chat.domain.usecases

import ru.yofik.athena.common.domain.model.exceptions.NoMoreItemsException
import ru.yofik.athena.common.domain.model.pagination.Pagination
import ru.yofik.athena.common.domain.repositories.MessageRepository
import timber.log.Timber
import javax.inject.Inject

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

        Timber.d("invoke: $pagination")

        messageRepository.cacheMessages(messages)

        if (!pagination.canLoadMore) {
            throw NoMoreItemsException("No more messages available")
        }

        return pagination
    }
}

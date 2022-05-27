package ru.yofik.athena.chatlist.domain.usecases

import javax.inject.Inject
import ru.yofik.athena.common.domain.model.exceptions.NoMoreItemsException
import ru.yofik.athena.common.domain.model.pagination.Pagination
import ru.yofik.athena.common.domain.repositories.ChatRepository
import timber.log.Timber

class RequestNextChatsPage
@Inject
constructor(
    private val chatRepository: ChatRepository,
) {
    suspend operator fun invoke(
        pageNumber: Int,
        pageSize: Int = Pagination.DEFAULT_PAGE_SIZE
    ): Pagination {
        val (chats, pagination) = chatRepository.requestGetPaginatedChats(pageNumber, pageSize)
        Timber.d("invoke: ${chats.joinToString("\n")}")

        chatRepository.cacheChats(chats)

        if (!pagination.canLoadMore) {
            throw NoMoreItemsException("No more chats available")
        }

        Timber.d("invoke: return")
        return pagination
    }
}

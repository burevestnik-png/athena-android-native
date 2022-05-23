package ru.yofik.athena.chatlist.domain.usecases

import javax.inject.Inject
import ru.yofik.athena.common.domain.model.exceptions.NoMoreItemsExceptions
import ru.yofik.athena.common.domain.model.pagination.Pagination
import ru.yofik.athena.common.domain.repositories.ChatRepository

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

        if (!pagination.canLoadMore) {
            throw NoMoreItemsExceptions("No more chats available")
        }

        chatRepository.cacheChats(chats)

        return pagination
    }
}

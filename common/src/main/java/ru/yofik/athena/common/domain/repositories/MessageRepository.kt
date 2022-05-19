package ru.yofik.athena.common.domain.repositories

import ru.yofik.athena.common.domain.model.pagination.PaginatedMessages

interface MessageRepository {
    suspend fun requestSendMessage(chatId: Long, text: String)
    suspend fun requestGetPaginatedMessages(
        chatId: Long,
        pageNumber: Int,
        pageSize: Int
    ): PaginatedMessages
    suspend fun requestDeleteDefiniteMessage(
        chatId: Long,
        messageId: Long,
        isGlobal: Boolean = false
    )
}

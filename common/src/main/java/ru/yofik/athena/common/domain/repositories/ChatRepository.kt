package ru.yofik.athena.common.domain.repositories

import ru.yofik.athena.common.domain.model.chat.Chat
import ru.yofik.athena.common.domain.model.pagination.PaginatedChats

interface ChatRepository {
    suspend fun requestGetPaginatedChats(pageNumber: Int, pageSize: Int): PaginatedChats
    suspend fun requestCreateChat(name: String, userId: Long): Chat
    suspend fun requestDeleteChat(chatId: Long)
}

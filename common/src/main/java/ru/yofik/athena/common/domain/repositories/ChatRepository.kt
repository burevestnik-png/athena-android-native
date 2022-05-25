package ru.yofik.athena.common.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.yofik.athena.common.domain.model.chat.Chat
import ru.yofik.athena.common.domain.model.pagination.PaginatedChats

interface ChatRepository {
    suspend fun requestGetPaginatedChats(pageNumber: Int, pageSize: Int): PaginatedChats
    suspend fun requestCreateChat(targetUserId: Long): Chat
    suspend fun requestDeleteChat(chatId: Long)

    fun getCachedChats(): Flow<List<Chat>>
    suspend fun cacheChats(chats: List<Chat>)
}

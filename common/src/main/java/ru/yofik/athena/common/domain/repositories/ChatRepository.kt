package ru.yofik.athena.common.domain.repositories

import ru.yofik.athena.common.domain.model.chat.Chat
import ru.yofik.athena.common.domain.model.chat.ChatWithDetails

interface ChatRepository {
    suspend fun requestGetAllChats(): List<Chat>
    suspend fun requestCreateChat(name: String, userId: Long): Chat
    suspend fun requestGetChat(id: Long): ChatWithDetails
}

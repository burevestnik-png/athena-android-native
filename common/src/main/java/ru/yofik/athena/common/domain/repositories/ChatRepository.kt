package ru.yofik.athena.common.domain.repositories

import ru.yofik.athena.common.domain.model.chat.Chat
import ru.yofik.athena.common.domain.model.chat.ChatWithDetails
import ru.yofik.athena.common.domain.model.message.Message

interface ChatRepository {
    suspend fun requestGetAllChats(): List<Chat>
    suspend fun requestCreateChat(name: String, userId: Long): Chat
    suspend fun requestGetChat(id: Long): ChatWithDetails
    suspend fun requestSendMessage(chatId: Long, text: String)
}

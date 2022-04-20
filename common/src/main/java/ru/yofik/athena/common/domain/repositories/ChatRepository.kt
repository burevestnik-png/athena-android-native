package ru.yofik.athena.common.domain.repositories

import ru.yofik.athena.common.domain.model.chat.Chat

interface ChatRepository {
    suspend fun requestGetAllChats(): List<Chat>
    suspend fun requestCreateChat(name: String, userId: Long): Chat
}
package ru.yofik.athena.common.data.cache

import kotlinx.coroutines.flow.Flow
import ru.yofik.athena.common.data.cache.model.CachedChatAggregate
import ru.yofik.athena.common.data.cache.model.CachedMessage
import ru.yofik.athena.common.data.cache.model.CachedUser

interface Cache {
    fun getUsers(): Flow<List<CachedUser>>
    suspend fun deleteAllUsers()
    suspend fun insertUsers(users: List<CachedUser>)

    fun getChats(): Flow<List<CachedChatAggregate>>
    suspend fun getChat(id: Long): CachedChatAggregate
    suspend fun insertChats(chatsAggregates: List<CachedChatAggregate>)
    suspend fun deleteAllChats()

    fun getAllMessagesByChatId(chatId: Long): Flow<List<CachedMessage>>
    suspend fun insertMessage(message: CachedMessage)
    suspend fun insertMessages(messages: List<CachedMessage>)
    suspend fun updateLastMessageByChatId(cachedMessage: CachedMessage)
    suspend fun deleteAllMessagesByChatId(chatId: Long)
    suspend fun deleteAllMessages()

    suspend fun cleanup()
}

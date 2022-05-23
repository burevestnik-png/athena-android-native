package ru.yofik.athena.common.data.cache

import kotlinx.coroutines.flow.Flow
import ru.yofik.athena.common.data.cache.model.CachedChatAggregate
import ru.yofik.athena.common.data.cache.model.CachedUser

interface Cache {
    fun getUsers(): Flow<List<CachedUser>>
    suspend fun insertUsers(users: List<CachedUser>)

    fun getChats(): Flow<List<CachedChatAggregate>>
    suspend fun insertChats(chatsAggregate: List<CachedChatAggregate>)
}

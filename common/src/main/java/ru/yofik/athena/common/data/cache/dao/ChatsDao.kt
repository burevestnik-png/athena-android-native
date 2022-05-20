package ru.yofik.athena.common.data.cache.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.yofik.athena.common.data.cache.model.CachedChat
import ru.yofik.athena.common.data.cache.model.CachedChatAggregate
import ru.yofik.athena.common.data.cache.model.CachedMessage
import ru.yofik.athena.common.data.cache.model.CachedUser

@Dao
abstract class ChatsDao {
    suspend fun insertChats(chatAggregates: List<CachedChatAggregate>) {
        for (chatAggregate in chatAggregates) {
            insertChatAggregate(chatAggregate.chat, chatAggregate.users, chatAggregate.lastMessage)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertChatAggregate(
        chat: CachedChat,
        users: List<CachedUser>,
        lastMessage: CachedMessage
    )

    @Transaction
    @Query("SELECT * FROM chats")
    abstract fun getAll(): Flow<List<CachedChatAggregate>>
}

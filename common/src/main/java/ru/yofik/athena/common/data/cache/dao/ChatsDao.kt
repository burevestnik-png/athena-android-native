package ru.yofik.athena.common.data.cache.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.yofik.athena.common.data.cache.model.CachedChat
import ru.yofik.athena.common.data.cache.model.CachedChatAggregate
import ru.yofik.athena.common.data.cache.model.CachedChatUserCrossRef

@Dao
abstract class ChatsDao {

    ///////////////////////////////////////////////////////////////////////////
    // INSERT
    ///////////////////////////////////////////////////////////////////////////

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertCachedChatUserCrossRef(crossRef: CachedChatUserCrossRef)

    suspend fun insertChats(chat: List<CachedChat>) {
        chat.forEach { insertChat(it) }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertChat(chat: CachedChat)

    ///////////////////////////////////////////////////////////////////////////
    // QUERY
    ///////////////////////////////////////////////////////////////////////////

    @Transaction
    @Query("SELECT * FROM chats")
    abstract fun getAll(): Flow<List<CachedChatAggregate>>

    @Transaction
    @Query("SELECT * FROM chats WHERE chatId = :id")
    abstract fun getById(id: Long): CachedChatAggregate

    ///////////////////////////////////////////////////////////////////////////
    // DELETE
    ///////////////////////////////////////////////////////////////////////////

    suspend fun deleteAll() {
        deleteAllChats()
        deleteAllChatUserCrossRef()
    }

    @Query("DELETE FROM chats") abstract suspend fun deleteAllChats()

    @Query("DELETE FROM chat_user_cross_ref") abstract suspend fun deleteAllChatUserCrossRef()
}

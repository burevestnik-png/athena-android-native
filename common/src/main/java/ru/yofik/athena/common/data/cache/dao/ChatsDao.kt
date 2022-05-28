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

    /*abstract suspend suspend fun insertChats(chatAggregates: List<CachedChatAggregate>) {
        for (chatAggregate in chatAggregates) {
            if (chatAggregate.lastMessage == null) {
                insertChatAggregateWithNullableLastMessage(chatAggregate.chat, chatAggregate.users)
            } else {
                insertChatAggregate(
                    chatAggregate.chat,
                    chatAggregate.users,
                    chatAggregate.lastMessage
                )
            }
        }
    }*/

    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertChatAggregateWithNullableLastMessage(
        chat: CachedChat,
        users: List<CachedUser>
    )*/

    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertChatAggregate(
        chat: CachedChat,
        users: List<CachedUser>,
        lastMessage: CachedMessage
    )*/

    ///////////////////////////////////////////////////////////////////////////
    // QUERY
    ///////////////////////////////////////////////////////////////////////////

    @Transaction
    @Query("SELECT * FROM chats")
    abstract fun getAll(): Flow<List<CachedChatAggregate>>

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

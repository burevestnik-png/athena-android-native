package ru.yofik.athena.common.data.cache.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.yofik.athena.common.data.cache.model.*

@Dao
abstract class ChatsDao {
    suspend fun insertChats(chatAggregates: List<CachedChatAggregate>) {
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
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertCachedChatUserCrossRef(crossRef: CachedChatUserCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertChatAggregateWithNullableLastMessage(
        chat: CachedChat,
        users: List<CachedUser>
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertChatAggregate(
        chat: CachedChat,
        users: List<CachedUser>,
        lastMessage: CachedMessage
    )

    @Transaction
    @Query("SELECT * FROM chats")
    abstract fun getAll(): Flow<List<CachedChatAggregate>>

    suspend fun deleteAll() {
        deleteAllChats()
        deleteAllChatUserCrossRef()
    }

    @Query("DELETE FROM chats") abstract suspend fun deleteAllChats()


    @Query("DELETE FROM chat_user_cross_ref")
    abstract suspend fun deleteAllChatUserCrossRef()
}

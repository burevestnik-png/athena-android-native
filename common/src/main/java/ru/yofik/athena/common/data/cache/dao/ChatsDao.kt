package ru.yofik.athena.common.data.cache.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.yofik.athena.common.data.cache.model.*

@Dao
internal interface ChatsDao {

    ///////////////////////////////////////////////////////////////////////////
    // INSERT
    ///////////////////////////////////////////////////////////////////////////

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCachedChatUserCrossRef(crossRef: CachedChatUserCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCachedChatLastMessageRef(crossRef: CachedChatLastMessageCrossRef)

    @Transaction
    suspend fun insertChatAggregates(chatAggregates: List<CachedChatAggregate>) {
        chatAggregates.forEach { chatAggregate ->
            insertChat(chatAggregate.chat)
            insertUsers(chatAggregate.users)

            if (chatAggregate.lastMessage != null) {
                insertMessage(chatAggregate.lastMessage)

                insertCachedChatLastMessageRef(
                    CachedChatLastMessageCrossRef(
                        chatId = chatAggregate.chat.chatId,
                        messageId = chatAggregate.lastMessage.messageId
                    )
                )
            }

            chatAggregate.users.forEach { user ->
                insertCachedChatUserCrossRef(
                    CachedChatUserCrossRef(
                        chatId = chatAggregate.chat.chatId,
                        userId = user.userId
                    )
                )
            }
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChat(chat: CachedChat)

    ///////////////////////////////////////////////////////////////////////////
    // METHOD DUPLICATION FROM OTHER DAOS DUE TO @TRANSACTIONAL PROBLEM
    ///////////////////////////////////////////////////////////////////////////

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<CachedUser>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: CachedMessage)

    ///////////////////////////////////////////////////////////////////////////
    // QUERY
    ///////////////////////////////////////////////////////////////////////////

    @Transaction
    @Query("SELECT * FROM chats")
    fun getAll(): Flow<List<CachedChatAggregate>>

    @Transaction
    @Query("SELECT * FROM chats WHERE chatId = :id")
    fun getById(id: Long): CachedChatAggregate

    ///////////////////////////////////////////////////////////////////////////
    // DELETE
    ///////////////////////////////////////////////////////////////////////////

    suspend fun deleteAll() {
        deleteAllChats()
        deleteAllChatUserCrossRef()
        deleteAllChatLastMessageCrossRef()
    }

    @Query("DELETE FROM chats")
    suspend fun deleteAllChats()

    @Query("DELETE FROM chat_user_cross_ref")
    suspend fun deleteAllChatUserCrossRef()

    @Query("DELETE FROM chat_last_message_cross_ref")
    suspend fun deleteAllChatLastMessageCrossRef()
}

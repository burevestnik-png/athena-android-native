package ru.yofik.athena.common.data.cache.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.yofik.athena.common.data.cache.model.*

@Dao
internal interface ChatsDao {

    ///////////////////////////////////////////////////////////////////////////
    // INSERT
    ///////////////////////////////////////////////////////////////////////////

    @Transaction
    suspend fun insertChatAggregates(chatAggregates: List<CachedChatAggregate>) {
        chatAggregates.forEach { chatAggregate -> insertChatAggregate(chatAggregate) }
    }

    suspend fun insertChatAggregate(chatAggregate: CachedChatAggregate) {
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

        insertCachedChatUserCrossRefs(
            crossRefs =
                chatAggregate.users.map {
                    CachedChatUserCrossRef(chatId = chatAggregate.chat.chatId, userId = it.userId)
                }
        )
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertChat(chat: CachedChat)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCachedChatUserCrossRefs(crossRefs: List<CachedChatUserCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCachedChatLastMessageRef(crossRef: CachedChatLastMessageCrossRef)

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

    @Transaction @Query("SELECT * FROM chats") fun getAll(): Flow<List<CachedChatAggregate>>

    @Transaction
    @Query("SELECT * FROM chats WHERE chatId = :id")
    fun getById(id: Long): CachedChatAggregate

    ///////////////////////////////////////////////////////////////////////////
    // DELETE
    ///////////////////////////////////////////////////////////////////////////

    suspend fun deleteAll() {
        deleteAllChatUserCrossRef()
        deleteAllChatLastMessageCrossRef()
        deleteAllChats()
    }

    @Query("DELETE FROM chats") suspend fun deleteAllChats()

    @Query("DELETE FROM chat_user_cross_ref") suspend fun deleteAllChatUserCrossRef()

    @Query("DELETE FROM chat_last_message_cross_ref") suspend fun deleteAllChatLastMessageCrossRef()
}

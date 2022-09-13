package ru.yofik.athena.common.data.cache.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.yofik.athena.common.data.cache.model.CachedChatLastMessageCrossRef
import ru.yofik.athena.common.data.cache.model.CachedMessage

@Dao
internal interface MessageDao {

    ///////////////////////////////////////////////////////////////////////////
    // INSERT
    ///////////////////////////////////////////////////////////////////////////

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: CachedMessage)

    ///////////////////////////////////////////////////////////////////////////
    // QUERY
    ///////////////////////////////////////////////////////////////////////////
    @Transaction
    @Query("SELECT * FROM messages WHERE chatId = :chatId")
    fun getAllFromDefiniteChat(chatId: Long): Flow<List<CachedMessage>>

    ///////////////////////////////////////////////////////////////////////////
    // UPDATE
    ///////////////////////////////////////////////////////////////////////////

    @Transaction
    suspend fun updateLastMessageForChat(cachedMessage: CachedMessage) {
        insertMessage(cachedMessage)
        deleteChatLastMessageCrossRefByChatId(cachedMessage.chatId)

        insertChat(
            CachedChatLastMessageCrossRef(
                chatId = cachedMessage.chatId,
                messageId = cachedMessage.messageId
            )
        )
    }

    @Query("DELETE FROM chat_last_message_cross_ref WHERE chatId = :chatId")
    suspend fun deleteChatLastMessageCrossRefByChatId(chatId: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChat(crossRef: CachedChatLastMessageCrossRef)

    ///////////////////////////////////////////////////////////////////////////
    // DELETE
    ///////////////////////////////////////////////////////////////////////////

    suspend fun deleteAll() {
        deleteAllMessages()
    }

    @Query("DELETE FROM messages")
    suspend fun deleteAllMessages()
}

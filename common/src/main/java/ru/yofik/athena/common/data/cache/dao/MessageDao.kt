package ru.yofik.athena.common.data.cache.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
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
    // DELETE
    ///////////////////////////////////////////////////////////////////////////

    suspend fun deleteAll() {
        deleteAllMessages()
    }

    @Query("DELETE FROM messages")
    suspend fun deleteAllMessages()
}

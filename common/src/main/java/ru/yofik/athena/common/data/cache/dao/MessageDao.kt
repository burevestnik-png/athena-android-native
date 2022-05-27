package ru.yofik.athena.common.data.cache.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface MessageDao {
    suspend fun deleteAll() {
        deleteAllMessages()
    }

    @Query("DELETE FROM messages")
    suspend fun deleteAllMessages()
}
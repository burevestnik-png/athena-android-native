package ru.yofik.athena.common.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chats")
data class CachedChat(
    @PrimaryKey(autoGenerate = false)
    val chatId: Long,
    val name: String
)
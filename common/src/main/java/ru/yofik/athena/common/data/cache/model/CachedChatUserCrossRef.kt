package ru.yofik.athena.common.data.cache.model

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "chat_user_cross_ref",
    primaryKeys = ["chatId", "userId"],
    indices = [Index("chatId"), Index("userId")]
)
data class CachedChatUserCrossRef(val chatId: Long, val userId: Long)

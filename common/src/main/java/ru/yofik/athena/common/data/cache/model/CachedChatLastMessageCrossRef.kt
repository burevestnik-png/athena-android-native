package ru.yofik.athena.common.data.cache.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "chat_last_message_cross_ref",
    primaryKeys = ["chatId", "messageId"],
    indices = [Index("chatId"), Index("messageId")]
)
data class CachedChatLastMessageCrossRef(
    val chatId: Long,
    val messageId: Long,
)

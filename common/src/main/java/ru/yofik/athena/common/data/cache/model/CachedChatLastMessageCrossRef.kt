package ru.yofik.athena.common.data.cache.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "chat_last_message_cross_ref",
    primaryKeys = ["chatId", "messageId"],
    foreignKeys =
        [
            ForeignKey(
                entity = CachedChat::class,
                parentColumns = ["chatId"],
                childColumns = ["chatId"],
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
            ),
            ForeignKey(
                entity = CachedMessage::class,
                parentColumns = ["messageId"],
                childColumns = ["messageId"],
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
            ),
        ],
    indices = [Index("chatId"), Index("messageId")]
)
data class CachedChatLastMessageCrossRef(
    val chatId: Long,
    val messageId: Long,
)

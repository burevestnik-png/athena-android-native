package ru.yofik.athena.common.data.cache.model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "chat_user_cross_ref",
    primaryKeys = ["chatId", "userId"],
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
                entity = CachedUser::class,
                parentColumns = ["userId"],
                childColumns = ["userId"],
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
            ),
        ]
)
data class CachedChatUserCrossRef(val chatId: Long, val userId: Long)

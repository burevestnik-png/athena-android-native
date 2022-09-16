package ru.yofik.athena.common.data.cache.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "chat_user_cross_ref",
    primaryKeys = ["chatId", "userId"],
    foreignKeys =
        [
            ForeignKey(
                entity = CachedChat::class,
                parentColumns = ["chatId"],
                childColumns = ["chatId"],
                // TODO: check onActions
                onDelete = ForeignKey.NO_ACTION,
                onUpdate = ForeignKey.CASCADE,
                deferred = true
            ),
            ForeignKey(
                entity = CachedUser::class,
                parentColumns = ["userId"],
                childColumns = ["userId"],
                onDelete = ForeignKey.NO_ACTION,
                onUpdate = ForeignKey.CASCADE,
                deferred = true
            ),
        ],
    indices = [Index("chatId"), Index("userId")]
)
data class CachedChatUserCrossRef(val chatId: Long, val userId: Long)

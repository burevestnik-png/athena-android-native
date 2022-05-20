package ru.yofik.athena.common.data.cache.model

import androidx.room.Entity

@Entity(primaryKeys = ["chatId", "userId"])
data class CachedChatUserCrossRef(val chatId: Long, val userId: Long)

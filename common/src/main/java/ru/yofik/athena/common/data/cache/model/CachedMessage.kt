package ru.yofik.athena.common.data.cache.model

import androidx.room.PrimaryKey

data class CachedMessage(
    @PrimaryKey(autoGenerate = false)
    val messageId: Long
)

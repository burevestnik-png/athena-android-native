package ru.yofik.athena.common.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class CachedUser(
    @PrimaryKey(autoGenerate = false)
    val userId: Long,

)
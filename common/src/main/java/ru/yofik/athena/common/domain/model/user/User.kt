package ru.yofik.athena.common.domain.model.user

import org.threeten.bp.LocalDateTime

/** Basic entity object */
data class User(
    val id: Long,
    val name: String,
    val login: String,
//    val isOnline: Boolean,
//    val lastOnlineTime: LocalDateTime
)

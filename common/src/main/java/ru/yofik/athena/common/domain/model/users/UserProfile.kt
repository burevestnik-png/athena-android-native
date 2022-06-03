package ru.yofik.athena.common.domain.model.users

import org.threeten.bp.LocalDateTime

/** Basic entity object which represents users of the messenger, not an app owner */
data class UserProfile(
    val id: Long,
    val name: String,
    val login: String,
    val isOnline: Boolean,
    val lastOnlineTime: LocalDateTime
)

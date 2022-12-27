package ru.yofik.athena.common.domain.model.users

import org.threeten.bp.LocalDateTime


/** Basic entity object which represents user who logged in messenger */
@Deprecated(message = "Old user")
data class User(
    val id: Long,
    val name: String,
    val login: String,
)

enum class Role {
    USER,
    ADMIN
}

data class UserV2(
    val id: Long,
    val email: String,
    val login: String,
    val role: Role,
    val isLocked: Boolean,
    val lockReason: String,
    val credentialsExpirationDate: LocalDateTime,
    val lastLoginDate: LocalDateTime,
)

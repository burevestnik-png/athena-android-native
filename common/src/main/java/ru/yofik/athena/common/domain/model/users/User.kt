package ru.yofik.athena.common.domain.model.users

import org.threeten.bp.LocalDateTime

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

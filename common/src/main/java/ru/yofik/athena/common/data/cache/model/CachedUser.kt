package ru.yofik.athena.common.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDateTime
import ru.yofik.athena.common.domain.model.users.Role
import ru.yofik.athena.common.domain.model.users.UserV2

@Entity(tableName = "users")
data class CachedUser(
    @PrimaryKey(autoGenerate = false) val userId: Long,
    val login: String,
    val name: String,
) {
    companion object {
        fun fromDomain(user: UserV2) =
            CachedUser(userId = user.id, login = user.login, name = user.email)
    }
}

fun CachedUser.toDomain() =
    UserV2(
        id = this.userId,
        login = this.login,
        email = this.name,
        lastLoginDate = LocalDateTime.now(),
        credentialsExpirationDate = LocalDateTime.now(),
        lockReason = "",
        isLocked = false,
        role = Role.USER
    )

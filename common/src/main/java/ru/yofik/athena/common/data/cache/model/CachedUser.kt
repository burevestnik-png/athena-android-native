package ru.yofik.athena.common.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.yofik.athena.common.domain.model.user.User

@Entity(tableName = "users")
data class CachedUser(
    @PrimaryKey(autoGenerate = false) val userId: Long,
    val login: String,
    val name: String
) {
    companion object {
        fun fromDomain(user: User): CachedUser {
            return CachedUser(userId = user.id, login = user.login, name = user.name)
        }

        fun toDomain(cachedUser: CachedUser): User {
            return User(id = cachedUser.userId, login = cachedUser.login, name = cachedUser.name)
        }
    }
}

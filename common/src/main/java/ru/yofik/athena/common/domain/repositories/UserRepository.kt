package ru.yofik.athena.common.domain.repositories

import ru.yofik.athena.common.domain.model.users.User

interface UserRepository {
    suspend fun requestUserActivation(code: String): String
    suspend fun requestGetUserInfo(): User

    fun getCachedUser(): User
    fun cacheUser(user: User)
    fun removeAllCache()

    fun hasAccess(): Boolean
}

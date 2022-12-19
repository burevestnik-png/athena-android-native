package ru.yofik.athena.common.domain.repositories

import ru.yofik.athena.common.domain.model.users.User

interface UserRepository {
    suspend fun requestUserActivation(code: String, userId: Long): String
    suspend fun requestGetUserInfo(): User

    fun getCachedUser(): User
    fun cacheAccessToken(token: String)
    fun cacheUser(user: User)
    fun removeAllCache()

    fun hasAccess(): Boolean
}

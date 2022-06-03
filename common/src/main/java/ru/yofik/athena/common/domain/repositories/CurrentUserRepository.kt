package ru.yofik.athena.common.domain.repositories

import ru.yofik.athena.common.domain.model.user.User

interface CurrentUserRepository {
    suspend fun requestCurrentUserActivation(code: String)
    suspend fun requestGetCurrentUserInfo()


    fun getCachedCurrentUser(): User
    fun cacheCurrentUser(user: User)
    fun removeAllCache()

    fun hasAccess(): Boolean
    fun removeAccessToken()
}

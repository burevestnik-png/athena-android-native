package ru.yofik.athena.common.domain.repositories

import ru.yofik.athena.common.domain.model.user.User

interface CurrentUserRepository {
    suspend fun requestActivate(code: String)
    suspend fun requestGetInfo()

    fun cache(user: User)
    fun getCache(): User
    suspend fun removeCache()

    fun hasAccess(): Boolean
    fun removeAccessToken()
}

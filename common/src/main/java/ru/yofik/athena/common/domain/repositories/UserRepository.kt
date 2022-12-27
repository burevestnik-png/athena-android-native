package ru.yofik.athena.common.domain.repositories

import ru.yofik.athena.common.domain.model.users.Tokens
import ru.yofik.athena.common.domain.model.users.User
import ru.yofik.athena.common.domain.model.users.UserV2

interface UserRepository {
    suspend fun requestGetCurrentUser(): UserV2
    suspend fun requestSignIn(code: String, userId: Long): Tokens
    suspend fun requestSignOut()
    suspend fun requestRefresh()

    suspend fun cacheUser(user: UserV2)
    suspend fun getCachedUser(): UserV2

    suspend fun cacheTokens(tokens: Tokens)

    suspend fun removeAllCache()

    fun hasAccess(): Boolean
}

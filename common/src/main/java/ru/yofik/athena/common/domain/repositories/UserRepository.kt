package ru.yofik.athena.common.domain.repositories

import ru.yofik.athena.common.domain.model.users.Tokens
import ru.yofik.athena.common.domain.model.users.User
import ru.yofik.athena.common.domain.model.users.UserV2

interface UserRepository {
    suspend fun requestGetCurrentUser(): UserV2
    suspend fun requestSignIn(code: String, userId: Long): Tokens
    suspend fun requestSignOut()
    suspend fun requestRefresh()

    fun cacheUser(user: User)
    fun getCachedUser(): User

    fun cacheTokens(tokens: Tokens)

    fun removeAllCache()

    fun hasAccess(): Boolean
}

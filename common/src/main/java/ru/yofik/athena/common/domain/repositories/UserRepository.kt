package ru.yofik.athena.common.domain.repositories

import ru.yofik.athena.common.domain.model.user.User

interface UserRepository {
    suspend fun requestActivateUser(code: String)
    suspend fun requestAuthUser()
    suspend fun requestGetAllUsers(): List<User>

    fun getCachedUser(): User
    fun removeCachedUser()
    fun removeUserAccessToken()

    // TODO refactor
    fun hasAccess(): Boolean
}

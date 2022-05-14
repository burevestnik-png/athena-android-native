package ru.yofik.athena.common.domain.repositories

import ru.yofik.athena.common.domain.model.user.User

interface UserRepository {
    suspend fun requestActivateUser(code: String)
    suspend fun requestAuthUser()
    suspend fun requestGetAllUsers(): List<User>

    suspend fun getCachedUsers(): List<User>
    suspend fun storeUsers(users: List<User>)

    fun storeCurrentUser(user: User)
    fun getCachedCurrentUser(): User
    fun removeCachedUser()

    fun hasAccess(): Boolean
    fun removeUserAccessToken()
}

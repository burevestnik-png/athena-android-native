package ru.yofik.athena.common.domain.repositories

import ru.yofik.athena.common.domain.model.user.User

interface UserRepository {
    suspend fun requestGetAllUsers(): List<User>

    suspend fun getCachedUsers(): List<User>
    suspend fun cacheUsers(users: List<User>)
}

package ru.yofik.athena.common.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.yofik.athena.common.domain.model.pagination.PaginatedUsers
import ru.yofik.athena.common.domain.model.users.User

interface UserProfileRepository {
    suspend fun requestGetPaginatedUsers(pageNumber: Int, pageSize: Int): PaginatedUsers
    suspend fun requestGetDefiniteUser(id: Long): User

    fun getCachedUsers(): Flow<List<User>>
    suspend fun cacheUsers(users: List<User>)
    suspend fun removeCachedUsers()
}

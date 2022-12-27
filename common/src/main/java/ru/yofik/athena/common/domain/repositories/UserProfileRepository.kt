package ru.yofik.athena.common.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.yofik.athena.common.domain.model.pagination.PaginatedUsers
import ru.yofik.athena.common.domain.model.users.UserV2

interface UserProfileRepository {
    suspend fun requestGetPaginatedUsersProfiles(pageNumber: Int, pageSize: Int): PaginatedUsers
    suspend fun requestGetDefiniteUserProfile(id: Long): UserV2

    fun getCachedUsers(): Flow<List<UserV2>>
    suspend fun cacheUsers(users: List<UserV2>)
    suspend fun removeAllCache()
}

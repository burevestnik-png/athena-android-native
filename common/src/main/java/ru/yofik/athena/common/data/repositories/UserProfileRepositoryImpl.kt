package ru.yofik.athena.common.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import ru.yofik.athena.common.data.api.http.model.user.mappers.ApiUserMapper
import ru.yofik.athena.common.data.api.http.model.userProfiles.UserProfileApi
import ru.yofik.athena.common.data.cache.Cache
import ru.yofik.athena.common.data.cache.model.CachedUser
import ru.yofik.athena.common.data.cache.model.toDomain
import ru.yofik.athena.common.domain.model.exceptions.NetworkException
import ru.yofik.athena.common.domain.model.pagination.PaginatedUsers
import ru.yofik.athena.common.domain.model.pagination.Pagination
import ru.yofik.athena.common.domain.model.users.UserV2
import ru.yofik.athena.common.domain.repositories.UserProfileRepository
import javax.inject.Inject

class UserProfileRepositoryImpl
@Inject
constructor(
    private val cache: Cache,
    private val apiUserMapper: ApiUserMapper,
    private val userProfileApi: UserProfileApi,
) : UserProfileRepository {

    ///////////////////////////////////////////////////////////////////////////
    // NETWORK
    ///////////////////////////////////////////////////////////////////////////

    override suspend fun requestGetPaginatedUsersProfiles(
        pageNumber: Int,
        pageSize: Int
    ): PaginatedUsers {
        try {
            val response = userProfileApi.getPaginatedUsers(pageNumber, pageSize)

            return PaginatedUsers(
                users = response.payload.users.map(apiUserMapper::mapToDomain),
                pagination =
                    Pagination(
                        currentPage = pageNumber + 1,
                        currentAmountOfItems = response.payload.users.size
                    )
            )
        } catch (exception: HttpException) {
            // TODO add exception parse
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }

    override suspend fun requestGetDefiniteUserProfile(id: Long): UserV2 {
        try {
            val response = userProfileApi.getDefiniteUser(id)
            return apiUserMapper.mapToDomain(response.payload)
        } catch (exception: HttpException) {
            // TODO add exception parse
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // CACHE
    ///////////////////////////////////////////////////////////////////////////

    override fun getCachedUsers(): Flow<List<UserV2>> {
        return cache.getUsers().map { users -> users.map { it.toDomain() } }
    }

    override suspend fun cacheUsers(users: List<UserV2>) {
        cache.insertUsers(users.map(CachedUser::fromDomain))
    }

    override suspend fun removeAllCache() {
        cache.deleteAllUsers()
    }
}

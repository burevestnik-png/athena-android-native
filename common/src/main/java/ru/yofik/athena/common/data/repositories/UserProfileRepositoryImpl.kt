package ru.yofik.athena.common.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import ru.yofik.athena.common.data.api.http.model.common.mappers.ApiUserMapper
import ru.yofik.athena.common.data.api.http.model.userProfiles.UserProfileApi
import ru.yofik.athena.common.data.cache.Cache
import ru.yofik.athena.common.data.cache.model.CachedUser
import ru.yofik.athena.common.data.cache.model.toDomain
import ru.yofik.athena.common.domain.model.exceptions.NetworkException
import ru.yofik.athena.common.domain.model.pagination.PaginatedUsers
import ru.yofik.athena.common.domain.model.pagination.Pagination
import ru.yofik.athena.common.domain.model.users.User
import ru.yofik.athena.common.domain.repositories.UserProfileRepository
import javax.inject.Inject

class UserProfileRepositoryImpl
@Inject
constructor(
    private val userProfileApi: UserProfileApi,
    private val apiUserMapper: ApiUserMapper,
    private val cache: Cache
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

    override suspend fun requestGetDefiniteUserProfile(id: Long): User {
        try {
            val response = userProfileApi.getDefiniteUser(id)
            return apiUserMapper.mapToDomain(response.user)
        } catch (exception: HttpException) {
            // TODO add exception parse
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // CACHE
    ///////////////////////////////////////////////////////////////////////////

    override fun getCachedUsers(): Flow<List<User>> {
        return cache.getUsers().map { users -> users.map { it.toDomain() } }
    }

    override suspend fun cacheUsers(users: List<User>) {
        cache.insertUsers(users.map(CachedUser::fromDomain))
    }

    override suspend fun removeAllCache() {
        cache.deleteAllUsers()
    }
}

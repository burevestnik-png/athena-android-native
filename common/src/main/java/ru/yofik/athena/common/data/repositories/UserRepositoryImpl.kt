package ru.yofik.athena.common.data.repositories

import javax.inject.Inject
import retrofit2.HttpException
import ru.yofik.athena.common.data.api.http.model.common.mappers.ApiUserMapper
import ru.yofik.athena.common.data.api.http.model.user.UserApi
import ru.yofik.athena.common.data.cache.Cache
import ru.yofik.athena.common.data.cache.model.CachedUser
import ru.yofik.athena.common.domain.model.exceptions.NetworkException
import ru.yofik.athena.common.domain.model.user.User
import ru.yofik.athena.common.domain.repositories.UserRepository

class UserRepositoryImpl
@Inject
constructor(
    private val userApi: UserApi,
    private val apiUserMapper: ApiUserMapper,
    private val cache: Cache
) : UserRepository {

    ///////////////////////////////////////////////////////////////////////////
    // NETWORK
    ///////////////////////////////////////////////////////////////////////////

    override suspend fun requestGetAllUsers(): List<User> {
        try {
            val response = userApi.getAllUsers()
            return response.payload.map(apiUserMapper::mapToDomain)
        } catch (exception: HttpException) {
            // TODO add exception parse
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }

    override suspend fun requestGetDefiniteUser(id: Long): User {
        try {
            val response = userApi.getDefiniteUser(id)
            return apiUserMapper.mapToDomain(response.user)
        } catch (exception: HttpException) {
            // TODO add exception parse
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // CACHE
    ///////////////////////////////////////////////////////////////////////////

    override suspend fun getCachedUsers(): List<User> {
        return cache.getUsers().map(CachedUser::toDomain)
    }

    override suspend fun cacheUsers(users: List<User>) {
        cache.insertUsers(users.map(CachedUser::fromDomain))
    }
}

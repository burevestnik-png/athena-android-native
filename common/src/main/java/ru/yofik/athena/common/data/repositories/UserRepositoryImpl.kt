package ru.yofik.athena.common.data.repositories

import javax.inject.Inject
import retrofit2.HttpException
import ru.yofik.athena.common.data.api.http.model.user.UserApi
import ru.yofik.athena.common.data.api.http.model.user.mappers.ApiAccessTokenMapper
import ru.yofik.athena.common.data.api.http.model.user.mappers.ApiUserMapper
import ru.yofik.athena.common.data.api.http.model.user.requests.ActivateUserRequest
import ru.yofik.athena.common.data.api.http.model.user.requests.AuthUserRequest
import ru.yofik.athena.common.data.cache.Cache
import ru.yofik.athena.common.data.cache.model.CachedUser
import ru.yofik.athena.common.data.preferences.Preferences
import ru.yofik.athena.common.domain.model.NetworkException
import ru.yofik.athena.common.domain.model.user.User
import ru.yofik.athena.common.domain.repositories.UserRepository
import timber.log.Timber

class UserRepositoryImpl
@Inject
constructor(
    private val userApi: UserApi,
    private val preferences: Preferences,
    private val accessTokenDtoMapper: ApiAccessTokenMapper,
    private val userDtoMapper: ApiUserMapper,
    private val cache: Cache
) : UserRepository {
    override suspend fun requestActivateUser(code: String) {
        try {
            val request = ActivateUserRequest(code)
            val response = userApi.activate(request)

            val accessToken = accessTokenDtoMapper.mapToDomain(response.payload)
            Timber.d("Response payload: $accessToken")

            preferences.putAccessToken(accessToken)
        } catch (exception: HttpException) {
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }

    // todo add custom exceptions
    override suspend fun requestAuthUser() {
        try {
            val request = AuthUserRequest(preferences.getAccessToken())
            val response = userApi.auth(request)

            val user = userDtoMapper.mapToDomain(response.payload)
            preferences.putCurrentUser(user)

            Timber.d("requestAuthUser: ${preferences.getCurrentUser()}")
        } catch (exception: HttpException) {
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }

    override suspend fun requestGetAllUsers(): List<User> {
        Timber.d("requestGetAllUsers: ")
        try {
            val response = userApi.getAllUsers()
            return response.payload.map(userDtoMapper::mapToDomain)
        } catch (exception: HttpException) {
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }

    override suspend fun getCachedUsers(): List<User> {
        Timber.d("getCachedUsers: ")
        return cache.getUsers().map(CachedUser::toDomain)
    }

    override suspend fun storeUsers(users: List<User>) {
        Timber.d("storeUsers: ")
        cache.storeUsers(users.map(CachedUser::fromDomain))
    }

    override fun storeCurrentUser(user: User) {
        preferences.putCurrentUser(user)
    }

    override fun getCachedCurrentUser(): User {
        return preferences.getCurrentUser()
    }

    override fun hasAccess(): Boolean {
        return with(preferences) {
            val cachedUser = getCachedCurrentUser()
            getAccessToken().isNotEmpty() &&
                cachedUser.login.isNotEmpty() &&
                cachedUser.name.isNotEmpty() &&
                cachedUser.id != -1L
        }
    }

    override fun removeCachedUser() {
        preferences.deleteCurrentUser()
    }

    override fun removeUserAccessToken() {
        preferences.deleteAccessToken()
    }

    // todo why this is not working
    @Throws(NetworkException::class)
    private inline fun wrapHttpException(block: UserRepository.() -> Unit) {
        try {
            block()
        } catch (exception: HttpException) {
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }
}

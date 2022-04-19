package ru.yofik.athena.common.data.repositories

import javax.inject.Inject
import retrofit2.HttpException
import ru.yofik.athena.common.data.api.model.user.UserApi
import ru.yofik.athena.common.data.api.model.user.mappers.AccessTokenDtoMapper
import ru.yofik.athena.common.data.api.model.user.mappers.UserDtoMapper
import ru.yofik.athena.common.data.api.model.user.requests.ActivateUserRequest
import ru.yofik.athena.common.data.api.model.user.requests.AuthUserRequest
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
    private val accessTokenDtoMapper: AccessTokenDtoMapper,
    private val userDtoMapper: UserDtoMapper
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
            preferences.putUserInfo(user)

            Timber.d("requestAuthUser: ${preferences.getUser()}")
        } catch (exception: HttpException) {
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }

    override suspend fun requestGetAllUsers(): List<User> {
        try {
            val response = userApi.getAllUsers()
            return response.payload.map(userDtoMapper::mapToDomain)
        } catch (exception: HttpException) {
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }

    override fun getCachedUser(): User {
        return preferences.getUser()
    }

    override fun hasAccess(): Boolean {
        return with(preferences) {
            val cachedUser = getCachedUser()
            getAccessToken().isNotEmpty() &&
                cachedUser.login.isNotEmpty() &&
                cachedUser.name.isNotEmpty() &&
                cachedUser.id != -1L
        }
    }

    override fun removeCachedUser() {
        preferences.deleteUserInfo()
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

package ru.yofik.athena.common.data

import javax.inject.Inject
import retrofit2.HttpException
import ru.yofik.athena.common.data.api.UserApi
import ru.yofik.athena.common.data.api.model.mappers.AccessTokenDtoMapper
import ru.yofik.athena.common.data.api.model.mappers.UserDtoMapper
import ru.yofik.athena.common.data.api.model.requests.ActivateUserRequest
import ru.yofik.athena.common.data.api.model.requests.AuthUserRequest
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
    override suspend fun activateUser(code: String) {
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

    override suspend fun authUser(): User {
        try {
            val request = AuthUserRequest(preferences.getAccessToken())
            val response = userApi.auth(request)

            return userDtoMapper.mapToDomain(response.payload)
        } catch (exception: HttpException) {
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
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

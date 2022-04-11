package ru.yofik.athena.common.data

import javax.inject.Inject
import ru.yofik.athena.common.data.api.UserApi
import ru.yofik.athena.common.data.api.model.mappers.AccessTokenDtoMapper
import ru.yofik.athena.common.data.api.model.mappers.MappingException
import ru.yofik.athena.common.data.api.model.mappers.UserDtoMapper
import ru.yofik.athena.common.data.api.model.requests.ActivateUserRequest
import ru.yofik.athena.common.data.api.model.requests.AuthUserRequest
import ru.yofik.athena.common.data.preferences.Preferences
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
    override suspend fun activateUser(code: String): Boolean {
        val request = ActivateUserRequest(code)
        val response = userApi.activate(request)

        return try {
            val accessToken = accessTokenDtoMapper.mapToDomain(response.payload)
            Timber.d("Response payload: $accessToken")

            preferences.putAccessToken(accessToken)
            true
        } catch (e: MappingException) {
            Timber.d("MappingException: ${e.message}")
            false
        }
    }

    override suspend fun authUser(): User {
        val request = AuthUserRequest(preferences.getAccessToken())
        val response = userApi.auth(request)

        return try {
            userDtoMapper.mapToDomain(response.payload)
        } catch (e: MappingException) {
            Timber.d("MappingException: ${e.message}")
            // TODO refactor
            throw RuntimeException()
        }
    }
}

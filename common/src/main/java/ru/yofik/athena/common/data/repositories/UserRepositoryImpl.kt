package ru.yofik.athena.common.data.repositories

import retrofit2.HttpException
import ru.yofik.athena.common.data.api.http.model.common.mappers.ApiUserMapper
import ru.yofik.athena.common.data.api.http.model.user.UserApi
import ru.yofik.athena.common.data.api.http.model.user.mappers.ApiAccessTokenMapper
import ru.yofik.athena.common.data.api.http.model.user.requests.ActivateUserRequest
import ru.yofik.athena.common.data.api.http.model.user.requests.AuthUserRequest
import ru.yofik.athena.common.data.preferences.Preferences
import ru.yofik.athena.common.domain.model.exceptions.NetworkException
import ru.yofik.athena.common.domain.model.users.User
import ru.yofik.athena.common.domain.repositories.UserRepository
import timber.log.Timber
import javax.inject.Inject

class UserRepositoryImpl
@Inject
constructor(
    private val preferences: Preferences,
    private val userApi: UserApi,
    private val apiAccessTokenMapper: ApiAccessTokenMapper,
    private val apiUserMapper: ApiUserMapper,
) : UserRepository {

    ///////////////////////////////////////////////////////////////////////////
    // NETWORK
    ///////////////////////////////////////////////////////////////////////////

    override suspend fun requestUserActivation(code: String): String {
        try {
            val request = ActivateUserRequest(code)
            val response = userApi.activate(request)

            val accessToken = apiAccessTokenMapper.mapToDomain(response.payload)
            Timber.d("Got accessToken $accessToken")

            return accessToken
        } catch (exception: HttpException) {
            // TODO add exception parse
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }

    override suspend fun requestGetUserInfo(): User {
        try {
            val request = AuthUserRequest(preferences.getAccessToken())
            val response = userApi.auth(request)

            val user = apiUserMapper.mapToDomain(response.payload)
            Timber.d("Got currentUser $user")

            return user
        } catch (exception: HttpException) {
            // TODO add exception parse
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // CACHE
    ///////////////////////////////////////////////////////////////////////////

    override fun cacheUser(user: User) {
        preferences.putCurrentUser(user)
    }

    override fun getCachedUser(): User {
        return preferences.getCurrentUser()
    }

    override fun cacheAccessToken(token: String) {
        preferences.putAccessToken(token)
    }

    override fun removeAllCache() {
        preferences.removeAccessToken()
        preferences.removeCurrentUser()
    }


    override fun hasAccess(): Boolean {
        return with(preferences) {
            getAccessToken().isNotEmpty() &&
                    getCurrentUserId() != -1L &&
                    getCurrentUserLogin().isNotEmpty() &&
                    getCurrentUserName().isNotEmpty()
        }
    }
}

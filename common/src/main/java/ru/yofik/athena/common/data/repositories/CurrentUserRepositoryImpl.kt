package ru.yofik.athena.common.data.repositories

import javax.inject.Inject
import retrofit2.HttpException
import ru.yofik.athena.common.data.api.http.model.user.UserApi
import ru.yofik.athena.common.data.api.http.model.user.mappers.ApiAccessTokenMapper
import ru.yofik.athena.common.data.api.http.model.user.mappers.ApiUserMapper
import ru.yofik.athena.common.data.api.http.model.user.requests.ActivateUserRequest
import ru.yofik.athena.common.data.api.http.model.user.requests.AuthUserRequest
import ru.yofik.athena.common.data.preferences.Preferences
import ru.yofik.athena.common.domain.model.exceptions.NetworkException
import ru.yofik.athena.common.domain.model.user.User
import ru.yofik.athena.common.domain.repositories.CurrentUserRepository
import timber.log.Timber

class CurrentUserRepositoryImpl
@Inject
constructor(
    private val preferences: Preferences,
    private val userApi: UserApi,
    private val apiAccessTokenMapper: ApiAccessTokenMapper,
    private val apiUserMapper: ApiUserMapper
) : CurrentUserRepository {

    ///////////////////////////////////////////////////////////////////////////
    // NETWORK
    ///////////////////////////////////////////////////////////////////////////

    override suspend fun requestActivate(code: String) {
        try {
            val request = ActivateUserRequest(code)
            val response = userApi.activate(request)

            val accessToken = apiAccessTokenMapper.mapToDomain(response.payload)
            Timber.d("Got accessToken $accessToken")

            preferences.putAccessToken(accessToken)
        } catch (exception: HttpException) {
            // TODO add exception parse
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }

    override suspend fun requestGetInfo() {
        try {
            val request = AuthUserRequest(preferences.getAccessToken())
            val response = userApi.auth(request)

            val user = apiUserMapper.mapToDomain(response.payload)
            Timber.d("Got currentUser $user")

            preferences.putCurrentUser(user)
        } catch (exception: HttpException) {
            // TODO add exception parse
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // CACHE
    ///////////////////////////////////////////////////////////////////////////

    override fun cache(user: User) {
        preferences.putCurrentUser(user)
    }

    override fun getCache(): User {
        return preferences.getCurrentUser()
    }

    override fun removeCache() {
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

    override fun removeAccessToken() {
        preferences.removeAccessToken()
    }
}

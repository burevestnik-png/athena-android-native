package ru.yofik.athena.common.data.repositories

import javax.inject.Inject
import retrofit2.HttpException
import ru.yofik.athena.common.data.api.http.model.user.UserApi
import ru.yofik.athena.common.data.api.http.model.user.mappers.ApiTokensMapper
import ru.yofik.athena.common.data.api.http.model.user.mappers.ApiUserMapper
import ru.yofik.athena.common.data.api.http.model.user.requests.SignInUserRequest
import ru.yofik.athena.common.data.preferences.Preferences
import ru.yofik.athena.common.domain.model.exceptions.NetworkException
import ru.yofik.athena.common.domain.model.users.Tokens
import ru.yofik.athena.common.domain.model.users.User
import ru.yofik.athena.common.domain.model.users.UserV2
import ru.yofik.athena.common.domain.repositories.UserRepository
import timber.log.Timber

class UserRepositoryImpl
@Inject
constructor(
    private val userApi: UserApi,
    private val preferences: Preferences,
    private val apiUserMapper: ApiUserMapper,
    private val apiTokensMapper: ApiTokensMapper,
) : UserRepository {

    ///////////////////////////////////////////////////////////////////////////
    // NETWORK
    ///////////////////////////////////////////////////////////////////////////

    override suspend fun requestGetCurrentUser(): UserV2 {
        try {
            val response = userApi.getUserInfo()

            return apiUserMapper.mapToDomain(response.payload)
        } catch (exception: HttpException) {
            // TODO add exception parse
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }

    override suspend fun requestSignIn(code: String, userId: Long): Tokens {
        try {
            val request = SignInUserRequest(code, userId)
            val response = userApi.signInUser(request)

            return apiTokensMapper.mapToDomain(response.payload)
        } catch (exception: HttpException) {
            // TODO add exception parse
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }

    override suspend fun requestSignOut() {}

    override suspend fun requestRefresh() {}

    ///////////////////////////////////////////////////////////////////////////
    // CACHE
    ///////////////////////////////////////////////////////////////////////////

    override fun cacheTokens(tokens: Tokens) = preferences.putTokens(tokens)

    override fun cacheUser(user: User) {
        preferences.putCurrentUser(user)
    }

    override fun getCachedUser(): User {
        return preferences.getCurrentUser()
    }

    override fun removeAllCache() {
        preferences.removeTokens()
        preferences.removeCurrentUser()
    }

    override fun hasAccess(): Boolean {
        return false
        //        return with(preferences) {
        //            getAccessToken().isNotEmpty() &&
        //                getCurrentUserId() != -1L &&
        //                getCurrentUserLogin().isNotEmpty() &&
        //                getCurrentUserName().isNotEmpty()
        //        }
    }
}
